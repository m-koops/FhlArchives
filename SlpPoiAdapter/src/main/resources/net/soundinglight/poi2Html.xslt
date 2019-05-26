<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:saxon="http://saxon.sf.net/" xmlns:sl="http://net.soundinglight"
	xmlns:poi="net.soundinglight.poi-v1"
	extension-element-prefixes="saxon" exclude-result-prefixes="saxon xsl xs">

	<xsl:output method="html" encoding="us-ascii" indent="yes" />

	<xsl:template match="/poi:document">
		<html>
			<head>
				<title>
					<xsl:text>POI </xsl:text>
					<xsl:value-of select="@name" />
				</title>
				<meta http-equiv="Content-Type" content="text/html; charset=us-ascii" />
				<style>
		body {
			font-family: SLP Times;
			font-size: 10px;
		}
		
		span.characterRun {
			border: solid 1px #C2C2E6;
			margin-left: 1px;
			margin-right: 1px;
		}
		
		td {
			padding: 3px;
		}
		
		img {
			max-height: 200px;
		}
				</style>
			</head>
			<body>
				<table width="100%" style="border-collapse: collapse;" border="1px">
					<thead style="text-align: left;">
						<tr>
							<th width="4%">id</th>
							<th width="5%">alignment</th>
							<th width="5%">indentFromLeft</th>
							<th width="5%">firstLineIndent</th>
							<th width="71%">content</th>
						</tr>
					</thead>
					<tbody>
						<xsl:apply-templates select="poi:paragraph" />
					</tbody>
				</table>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/poi:document/poi:paragraph">
		<tr>
			<td><xsl:value-of select="@id" /></td>
			<td><xsl:value-of select="@alignment" /></td>
			<td><xsl:value-of select="@indentFromLeft" /></td>
			<td><xsl:value-of select="@firstLineIndent" /></td>
			<td><xsl:apply-templates select="poi:characterRun|poi:picture" /></td>
		</tr>
	</xsl:template>
	
	<xsl:template match="/poi:document/poi:paragraph/poi:characterRun">
		<span class="characterRun"><xsl:value-of select="replace(text(), '\t', '&lt;tab&gt;')" /></span>
	</xsl:template>

	<xsl:template match="/poi:document/poi:paragraph/poi:picture">
		<div>
			<xsl:text>Picture (mimeType=</xsl:text>
			<xsl:value-of select="@mimeType" />
			<xsl:text>, hor. scale=</xsl:text>
			<xsl:value-of select="@horizontalScalingFactor" />
			<xsl:text>, vert. scale=</xsl:text>
			<xsl:value-of select="@verticalScalingFactor" />
			<xsl:text>, width=</xsl:text>
			<xsl:value-of select="@dxaGoalMm" />
			<xsl:text> mm</xsl:text>
			<xsl:text>, heigth=</xsl:text>
			<xsl:value-of select="@dyaGoalMm" />
			<xsl:text> mm</xsl:text>
			<xsl:text>)</xsl:text>
		</div>
		<img>
			<xsl:attribute name="src">
				<xsl:text>data:</xsl:text>
				<xsl:value-of select="@mimeType" />
				<xsl:text>;base64,</xsl:text>
				<xsl:value-of select="text()"/>
			</xsl:attribute>
		</img>
	</xsl:template>
	
</xsl:stylesheet>