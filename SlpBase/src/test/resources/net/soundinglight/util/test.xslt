<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:saxon="http://saxon.sf.net/" 
	xmlns:slp="urn:slp:tapelist:v1" extension-element-prefixes="saxon" 
	exclude-result-prefixes="saxon xsl xs">

	<xsl:output method="text" indent="yes" />

	<xsl:include href="../identity.xslt" />
	
	<xsl:template match="slp:root">test</xsl:template>
</xsl:stylesheet>