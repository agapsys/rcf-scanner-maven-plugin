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
import com.agapsys.mvn.scanner.ScannerDefs;
import com.agapsys.mvn.scanner.SourceDirectoryScanner;

/**
 * RCF implementation of {@linkplain ScannerDefs}
 */
public class RcScannerDefs extends ScannerDefs {
    // STATIC SCOPE ============================================================
    private static final RcScannerDefs SINGLETON = new RcScannerDefs();

    public static RcScannerDefs getInstance() {
        return SINGLETON;
    }

    static final String OPTION_INCLUDE_DEPENDENCIES = "includeDependencies";
    static final String OPTION_INCLUDE_TESTS        = "includeTests";

    static final String CONTROLLER_ANNOTATION_CLASS_NAME = "com.agapsys.rcf.WebController";

    public static void log(String message, Object...msgArgs) {
        if (msgArgs.length > 0)
            message = String.format(message, msgArgs);

        System.out.println(message);
    }
    // =========================================================================

    // INSTANCE SCOPE ==========================================================
    private RcScannerDefs() {}

    @Override
    public SourceDirectoryScanner getSourceDirectoryScanner() {
        return RcSourceDirectoryScanner.getInstance();
    }

    @Override
    public ScanInfo getScanInfoInstance() {
        return new RcScanInfo();
    }

    @Override
    public String getEmbeddedScanInfoFilename() {
        return "rcf.info";
    }
    // =========================================================================
}
