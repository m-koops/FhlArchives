<?xml version="1.0"?>
<!--
	(c) 2014 Soundinglight Publishing
	All rights reserved.
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="@*|node()" name="copy">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="@*|node()" mode="copy">
		<xsl:call-template name="copy" />
	</xsl:template>
</xsl:stylesheet>
