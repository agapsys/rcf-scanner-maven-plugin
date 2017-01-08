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

import com.agapsys.mvn.scanner.parser.ParsingException;
import static com.agapsys.rcf.scanner.TestUtils.getFile;
import java.io.File;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class SourceFileInfoTest {
    
    @Test
    public void testValid() throws ParsingException {
        File srcFile;
        Set<String> expectedClasses;
        Set<String> scannedClasses;
        
        // ---------------------------------------------------------------------
        expectedClasses = TestUtils.getStringSet(
            "valid.Controller1",
            "valid.Controller1.InnerController"
        );
        
        srcFile = new File(getFile(Defs.LIB_SRC_DIR, "valid"), "Controller1.java");
        scannedClasses = TestUtils.scanJpaClasses(srcFile);
        Assert.assertEquals(expectedClasses, scannedClasses);
        // ---------------------------------------------------------------------
        
        // ---------------------------------------------------------------------
        expectedClasses = TestUtils.getStringSet(
            "valid.Controller2",
            "valid.Controller2.InnerController"
        );
        
        srcFile = new File(getFile(Defs.LIB_SRC_DIR, "valid"), "Controller2.java");
        scannedClasses = TestUtils.scanJpaClasses(srcFile);
        Assert.assertEquals(expectedClasses, scannedClasses);
        // ---------------------------------------------------------------------
    }
    
    @Test
    public void testInvalid() {
        Throwable error = null;

        try {
            TestUtils.scanJpaClasses(new File(getFile(Defs.LIB_SRC_DIR, "invalid"), "InvalidNesting.java.src"));
        } catch (ParsingException t) {
            error = t;
        }
        
        Assert.assertNotNull(error);
        Assert.assertEquals("Nested class must be static nested: invalid.InvalidNesting.InvalidNestingController", error.getMessage());
        
    }
}
