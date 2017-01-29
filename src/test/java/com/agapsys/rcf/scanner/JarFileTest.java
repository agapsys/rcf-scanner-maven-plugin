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
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class JarFileTest {
    
    @Test
    public void test() throws ParsingException {
        
        Set<String> scannedClasses = TestUtils.getEmbeddedInfo(Defs.LIB_JAR);
        Set<String> expectedClasses = TestUtils.getStringSet(
            "valid.Controller1",
            "valid.Controller1.InnerController",
            "valid.Controller2",
            "valid.Controller2.InnerController"
        );
        
        Assert.assertEquals(expectedClasses, scannedClasses);
    }
}
