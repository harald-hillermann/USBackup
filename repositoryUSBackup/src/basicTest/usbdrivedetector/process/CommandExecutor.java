/*
 * Copyright 2014 samuelcampos.
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
package basicTest.usbdrivedetector.process;

import java.io.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author samuelcampos
 */
public class CommandExecutor implements Closeable {

    private final BufferedReader input;
    private final Process process;

    public CommandExecutor(final String command) throws IOException {
        
        process = Runtime.getRuntime().exec(command);

        input = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    public void processOutput(final Consumer<String> method) throws IOException{
        String outputLine;
        while ((outputLine = this.readOutputLine()) != null) {
            method.accept(outputLine);
        }
    }

    public boolean checkOutput(final Predicate<String> method) throws IOException{
        String outputLine;
        while ((outputLine = this.readOutputLine()) != null) {
            if (method.test(outputLine)) {
                return true;
            }
        }

        return false;
    }

    private String readOutputLine() throws IOException {
        if(input == null) {
            throw new IllegalStateException("You need to call 'executeCommand' method first");
        }
        
         final String outputLine = input.readLine();
         
         if(outputLine != null) {
             return outputLine.trim();
         }
         
         return null;
    }

    @Override
    public void close() throws IOException {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
               System.out.println ( e.getMessage () );            }
        }

        if (process != null) {
            process.destroy();
        }
    }

}
