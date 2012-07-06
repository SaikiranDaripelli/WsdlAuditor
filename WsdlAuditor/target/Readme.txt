This tool can be used to run audit rules on a WSDL files, mainly for Naming Conventions.
Since this tools is devised to be a Rule Engine rather than providing a set of rules, this tool can be used to run any kind of rules on wsdl.
Also this tool is completely extensible, i.e. all the core classes are instantiated using factory backed by Configuration.xml which can supplied as input argument.
By Default Tool generates a HTML output, but you can supply any output user, which uses the tool output to generated any kind of file.

Eclipse Plugin for using this tool with Eclipse:

Update Site: http://saikirandaripelli.github.com/WsdlAuditor/WsdlAuditorUpdateSite/
Or
Zipped Update Site: http://wsdlauditor.sourceforge.net/WsdlAuditorUpdateSite.zip

More Detailed Information on formats of Rule Definition file, Configuration file,
and for detailed information on how to extends, configure or create a new rule operator, please visit link,

http://saikirandaripelli.github.com/WsdlAuditor/ 


Usage: 
   java -jar WsdlAuditor.jar document=<WSDL Document path/URL> ruledef=<Ruledefinition xml Path/URL> outputdir=<output directory>
   
   If tool needs to be used in compare mode, add a parameter 
   
   comparedoc=<old wsdl path>
   
   for Supplying Configuration to tool, which is used to configure  new OutputUsers, Rule Operator Executors, Switching off Default Output.
   add following argument,
   config=<configuration xml path>
 
 