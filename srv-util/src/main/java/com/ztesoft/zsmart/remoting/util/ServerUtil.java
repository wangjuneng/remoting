/**
 * Copyright [yyyy] [name of copyright owner]
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ztesoft.zsmart.remoting.util;

import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * <Description>只提供Server程序依赖，目的为了拆解客户端依赖，尽可能减少客户端的依赖 <br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.util <br>
 */
public class ServerUtil {

    public static Options buildCommandlineOptions(final Options options) {
        Option opt = new Option("h", "help", false, "Print help");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("n", "namesrvAddr", true, "Name server address list,eg:192.168.1.1:9876");
        opt.setRequired(true);
        options.addOption(opt);

        return options;
    }

    /**
     * Description: 解析commandLine<br>
     * 
     * @author wang.jun<br>
     * @taskId <br>
     * @param appName
     * @param args
     * @param options
     * @param parser
     * @return <br>
     */
    public static CommandLine parseCmdLine(final String appName, String[] args, Options options,
        CommandLineParser parser) {
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption("h")) {
                hf.printHelp(appName, options, true);
            }
        }
        catch (ParseException e) {
            hf.printHelp(appName, options, true);
        }

        return commandLine;
    }

    /**
     * Description:commandLine -> properties <br>
     * 
     * @author wang.jun<br>
     * @taskId <br>
     * @param commandLine
     * @return <br>
     */
    public static Properties commandLine2Properties(final CommandLine commandLine) {
        Properties properties = new Properties();
        Option[] opts = commandLine.getOptions();

        if (opts != null) {
            for (Option opt : opts) {
                String name = opt.getLongOpt();
                String value = commandLine.getOptionValue(name);
                if (value != null) {
                    properties.setProperty(name, value);
                }
            }
        }
        return properties;
    }

}
