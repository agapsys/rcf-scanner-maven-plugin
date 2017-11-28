/*
 * Copyright 2016 Agapsys Tecnologia Ltda-ME.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agapsys.rcf.scanner;

import com.agapsys.mvn.scanner.SourceDirectoryScanner;
import com.agapsys.mvn.scanner.parser.AnnotationInfo;
import com.agapsys.mvn.scanner.parser.ClassInfo;
import com.agapsys.mvn.scanner.parser.ParsingException;
import static com.agapsys.rcf.scanner.RcScannerDefs.log;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * RCF implementation of {@linkplain com.agapsys.mvn.scanner.SourceDirectoryScanner}
 */
public class RcSourceDirectoryScanner extends SourceDirectoryScanner {
    // STATIC SCOPE ============================================================
    private static RcSourceDirectoryScanner SINGLETON = new RcSourceDirectoryScanner();

    public static RcSourceDirectoryScanner getInstance() {
        return SINGLETON;
    }

    private static boolean _matchesControllerAnnotation(Collection<AnnotationInfo> annotationInfoCollection) {

        for (AnnotationInfo annotationInfo : annotationInfoCollection) {
            if (annotationInfo.className.equals(RcScannerDefs.CONTROLLER_ANNOTATION_CLASS_NAME))
                return true;
        }

        return false;
    }

	private static List<AnnotationInfo> _getControllerAnnotations(ClassInfo classInfo) {
		List<AnnotationInfo> controllerAnnotations = new LinkedList<AnnotationInfo>();

		for (AnnotationInfo annotationInfo : classInfo.annotations) {
			if (annotationInfo.className.equals(RcScannerDefs.CONTROLLER_ANNOTATION_CLASS_NAME)) {
				controllerAnnotations.add(annotationInfo);
			}
		}

		return controllerAnnotations;
	}
    // =========================================================================

    // INSTANCE SCOPE ==========================================================
    private RcSourceDirectoryScanner() {}

	private final Map<String, ClassInfo> mappedControllers = new LinkedHashMap<String, ClassInfo>();

	@Override
	public void reset() {
		super.reset();
		mappedControllers.clear();
	}

    @Override
    protected boolean shallBeIncluded(ClassInfo classInfo) throws ParsingException {
        if (!_matchesControllerAnnotation(classInfo.annotations))
			return false;

        if (!classInfo.isTopClass() && !classInfo.isStaticNested)
            throw new ParsingException("Nested class must be static nested: %s", classInfo.className);

        return true;
    }

    @Override
    protected void beforeInclude(ClassInfo classInfo) {

		for (AnnotationInfo mappingAnnotation : _getControllerAnnotations(classInfo)) {
			String mapping = mappingAnnotation.memberValue == null ? classInfo.getSimpleName() : mappingAnnotation.memberValue;

			ClassInfo mappedClassInfo = mappedControllers.get(mapping);

			if (mappedClassInfo != null)
				throw new RuntimeException(String.format("Dupplicate mapping for '%s' (classes: '%s' and '%s')", mapping, mappedControllers.get(mapping).className, classInfo.className));

			mappedControllers.put(mapping, classInfo);
		}

		log("Detected controller: %s", classInfo.className);
    }
    // =========================================================================

}
