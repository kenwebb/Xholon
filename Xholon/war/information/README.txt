Firefox 3.0.x gives a "may not load data" Security error.
It is restricted in its ability to use the file info2html.xsl when that file
is in a different directopry from the .xml file that uses it.
The codmplete error message is:
Security Error: Content at file:///C:/Xholon/config/HelloWorld/Information_helloworld.xml
may not load data from file:///C:/Xholon/information/info2html.xsl.

Here's how you can change the "security.fileuri.strict_origin_policy" property
to allow you to access local web content files.  To do this,
  * In the location bar, type "about:config". 
    This lists the configuration properties for your Firefox 3 browser.  *BE CAREFUL*
  * Search for the "security.fileuri.strict_origin_policy" property
  * Set to false
  * Re-start your Firefox browser, and determine if you can now access local files.

source: http://ubiquity-xforms.googlecode.com/svn/wiki/CrossDomainSubmissionDeployment.wiki

Alternatively, you can copy the .xsl file into the same directory as the .xml file.
