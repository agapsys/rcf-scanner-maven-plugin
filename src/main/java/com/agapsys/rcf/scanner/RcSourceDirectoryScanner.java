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

/**
 * RCF implementation of {@linkplain com.agapsys.mvn.scanner.SourceDirectoryScanner}
 */
public class RcSourceDirectoryScanner extends SourceDirectoryScanner {
    // STATIC SCOPE ============================================================
    private static RcSourceDirectoryScanner SINGLETON = new RcSourceDirectoryScanner();

    public static RcSourceDirectoryScanner getInstance() {
        return SINGLETON;
    }

    static AnnotationInfo getAnnotationInfo(Collection<AnnotationInfo> annotationInfoCollection, String annotationClassName) {
        for (AnnotationInfo annotationInfo : annotationInfoCollection) {
            if (annotationInfo.className.equals(annotationClassName))
                return annotationInfo;
        }

        return null;
    }
    // =========================================================================

    // INSTANCE SCOPE ==========================================================
    private RcSourceDirectoryScanner() {}

    @Override
    protected boolean shallBeIncluded(ClassInfo classInfo) throws ParsingException {
        AnnotationInfo controllerAnnotationInfo = getAnnotationInfo(classInfo.annotations, RcScannerDefs.CONTROLLER_ANNOTATION_CLASS_NAME);

        if (controllerAnnotationInfo == null)
            return false;

        if (!classInfo.isTopClass() && !classInfo.isStaticNested)
            throw new ParsingException("Nested class must be static nested: %s", classInfo.className);

        return true;
    }

    @Override
    protected void beforeInclude(ClassInfo classInfo) {
        log("Detected controller: %s", classInfo.className);
    }
    // =========================================================================

}
