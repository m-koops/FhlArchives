<?xml version="1.0" encoding="us-ascii" ?>
<!--
	(c) 2014 Soundinglight Publishing
	All rights reserved.
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:slp="net.soundinglight.tapelist"
                exclude-result-prefixes="slp">

    <xsl:include href="session2html.xslt"/>

    <xsl:output method="html" encoding="us-ascii" indent="yes" omit-xml-declaration="yes"/>

    <xsl:template match="/slp:event">
        <html>
            <head>
                <title>
                    <xsl:text>Tapelist </xsl:text>
                    <xsl:value-of select="@name" />
                </title>
                <meta http-equiv="Content-Type" content="text/html; charset=us-ascii"/>
                <style>
                    body {
                    font-family: Times New Roman;
                    }

                    span.label {
                    color: FireBrick;
                    font-size: 90%;
                    }

                    div.paragraphId {
                    float: right;
                    margin: 2px;
                    font-size: 50%;
                    color: purple;
                    }

                    table.index {
                    font-family: SLP Times;
                    border: solid 1px #FFE8E8;
                    margin-bottom: 25px;
                    }

                    a {
                    text-decoration: none;
                    color: black;
                    }

                    /* --- DEBUG --- */

                    span.fragment.sacred {
                    border: solid 1px #5a9204e6;
                    }

                    span.fragment {
                    border: solid 1px #C2C2E6;
                    margin: 1px;
                    }

                    div.paragraph {
                    border: solid 1px #FFE8E8;
                    }

                    span.bulletLabel {
                    border: 1px solid purple;
                    }
                </style>
                <xsl:call-template name="sessionStyles"/>
            </head>
            <body>
                <xsl:call-template name="index"/>
                <xsl:apply-templates select="slp:preamble"/>
                <xsl:apply-templates select="slp:session"/>
                <xsl:apply-templates select="slp:conclusion"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="index">
        <div id="index">
            <H2>Index</H2>
            <table class='fullWidth noMargin index'>
                <colgroup>
                    <col style='width: 8%;'/>
                    <col style='width: 92%;'/>
                </colgroup>
                <tbody>
                    <xsl:apply-templates select="//slp:session[slp:details/@available='true']" mode="index"/>
                </tbody>
            </table>
        </div>
    </xsl:template>


    <xsl:template match="slp:session" mode="index">
        <tr>
            <td>
                <xsl:value-of select="slp:details/@id"/>
            </td>
            <td>
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:text>#session-</xsl:text>
                        <xsl:value-of select="slp:details/@id"/>
                    </xsl:attribute>
                    <xsl:value-of select="slp:details/slp:title"/>
                    <xsl:if test="slp:details/slp:subTitle">
                        <xsl:text> </xsl:text>
                        <xsl:value-of select="slp:details/slp:subTitle"/>
                    </xsl:if>
                </xsl:element>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="slp:preamble">
        <xsl:apply-templates select="slp:paragraph"/>
    </xsl:template>

    <xsl:template match="slp:conclusion">
        <xsl:apply-templates select="slp:paragraph"/>
    </xsl:template>


</xsl:stylesheet>