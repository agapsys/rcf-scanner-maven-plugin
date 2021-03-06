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

import static com.agapsys.rcf.scanner.TestUtils.getFile;
import java.io.File;

public class Defs {
    // CLASS SCOPE =============================================================
    public static final File LIB_JAR;
    public static final File LIB_SRC_DIR;
    
    static {
        LIB_JAR = getFile("sample-lib/sample-lib-0.1.0.jar");
        LIB_SRC_DIR = getFile("sample-lib/src/src/main/java");
    }
    // =========================================================================

    // INSTANCE SCOPE ==========================================================
    private Defs() {}
    // =========================================================================
}
