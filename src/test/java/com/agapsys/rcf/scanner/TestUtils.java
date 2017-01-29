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

import com.agapsys.mvn.scanner.ScanInfo;
import com.agapsys.mvn.scanner.parser.ClassInfo;
import com.agapsys.mvn.scanner.parser.ParsingException;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class TestUtils {
    private TestUtils() {}

    private static String getPath(String posixPath) {
        String separator = System.getProperty("file.separator");
        return posixPath.replaceAll(Pattern.quote("/"), separator);
    }

    public static File getFile(File parent, String child) {
        return new File(parent, getPath(child));
    }

    public static File getFile(String posixPath) {
        return new File(getPath(posixPath));
    }

    public static Set<String> getEmbeddedInfo(File jarFile) throws ParsingException {
        RcScannerDefs defs = RcScannerDefs.getInstance();
        ScanInfo scanInfo = defs.getScanInfoInstance();
        scanInfo.addJar(jarFile, defs.getEmbeddedScanInfoFilePath(), defs.getEmbeddedScanInfoFileEncoding());
        return scanInfo.getEntries();
    }

    public static Set<String> scanJpaClasses(File srcDirOrFile) throws ParsingException {

        Set<ClassInfo> classInfoSet = RcSourceDirectoryScanner.getInstance().getFilteredClasses(srcDirOrFile);
        Set<String> classNameSet = new TreeSet<String>();

        for (ClassInfo classInfo : classInfoSet) {
            classNameSet.add(classInfo.className);
        }

        return classNameSet;
    }

    public static Set<String> getStringSet(String...elements) {
        Set<String> stringList = new TreeSet<String>();

        for (String element : elements) {
            if (element == null || element.trim().isEmpty())
                throw new IllegalArgumentException("Null/Empty element");

            stringList.add(element);
        }

        return stringList;
    }
}
