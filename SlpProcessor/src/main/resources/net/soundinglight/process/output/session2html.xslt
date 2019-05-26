<?xml version="1.0" encoding="us-ascii" ?>
<!--
	(c) 2014 Soundinglight Publishing
	All rights reserved.
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:slp="net.soundinglight.tapelist"
                exclude-result-prefixes="slp">

    <xsl:template name="sessionStyles">
        <style type="text/css">

            .session {
            margin-top: 20px;
            }

            .sessionHeader {
            font-family: SLP Times;
            }

            .sessionDetails {
            margin: 10px;
            }

            .title {
            font-size: 120%;
            font-weight: bold;
            text-decoration:underline;
            text-align: center;
            }

            .subTitle {
            font-size: 110%;
            font-weight: bold;
            text-decoration:underline;
            text-align: center;
            }

            .sacred {
            font-family: SLP Times;
            }

            .sacredCaps {
            font-family: SLP Times SmallCaps;
            }

            .tab {
            margin-left: 30px;
            }

            .paragraph {
            margin-top: 5px;
            }

            .paragraph .label {
            color: black;
            }

            .track {
            margin-bottom:15px;
            }

            .duration{
            margin-top:15px;
            }

            .bold {
            font-weight: bold;
            }

            .italic {
            font-style: italic;
            }

            .bold_italic {
            font-weight: bold;
            font-style: italic;
            }

            .leftAligned {
            text-align: left;
            }

            .largeText {
            font-size: 150%;
            }

            .rightAligned {
            text-align: right;
            }

            .centered {
            text-align: center;
            }

            .justified {
            text-align: justify;
            }

            .fullWidth {
            width: 100%;
            }

            .noMargin {
            margin: 0;
            }

            .bordered {
            border: 1px solid black;
            }

            .clear {
            clear: both;
            }

            div.leftColumn {
            width: 35px;
            float: left;
            }

            div.rightColumn {
            margin-left: 35px;
            }

            p {
            margin-top: 0;
            margin-bottom: 3px;
            }

            p.indented {
            margin-left: 100px;
            margin-right: 100px;
            }

            p.empty {
            height: 1em;
            }

            span.bulletLabel {
            margin-right: 0.5em;
            }

        </style>
    </xsl:template>

    <xsl:template match="slp:session[slp:details/@available='true']">
        <div class='session'>
            <xsl:attribute name="id">
                <xsl:text>session-</xsl:text>
                <xsl:value-of select="slp:details/@id"/>
            </xsl:attribute>
            <div class='sessionHeader'>
                <div>
                    <div class='leftColumn'>
                        <a href="#index">&#x25B2; Back to top</a>
                    </div>
                    <div class='rightColumn'>
                        <div class='fullWidth bordered'>
                            <xsl:apply-templates select="slp:details"/>
                        </div>
                    </div>
                    <div class='clear'></div>
                </div>
                <div>
                    <div class='leftColumn'></div>
                    <div class='rightColumn'>
                        <div class='title'>
                            <xsl:value-of select="slp:details/slp:title"/>
                        </div>
                        <div class='subTitle'>
                            <xsl:value-of select="slp:details/slp:subTitle"/>
                        </div>
                    </div>
                    <div class='clear'></div>
                </div>
                <xsl:apply-templates select="slp:details/slp:notes"/>
                <br/>
                <br/>
            </div>

            <xsl:apply-templates select="slp:tracks"/>
        </div>
    </xsl:template>

    <xsl:template match="slp:session[slp:details/@available='false']">
        <div class='session'>
            <xsl:attribute name="id">
                <xsl:text>session-</xsl:text>
                <xsl:value-of select="slp:details/@id"/>
            </xsl:attribute>
            <div class='sessionHeader'>
                <div class='leftColumn'>
                    <a href="#index">&#x25B2; Back to top</a>
                </div>
                <div class='rightColumn'>
                    <div class='fullWidth bordered'>
                        <div class='sessionDetails'>
                            <span class='bold largeText'>
                                Session
                                <xsl:value-of select="slp:details/@id"/>
                            </span>
                            <span>is unavailable.</span>
                        </div>
                    </div>
                </div>
                <div class='clear'></div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="slp:notes">
        <br/>
        <div>
            <div class='leftColumn'></div>
            <div class='rightColumn'>
                <xsl:for-each select="slp:note">
                    <div>
                        <span class="bold">Referred notes: </span>
                        <span><xsl:value-of select="."/></span>
                    </div>
                </xsl:for-each>
            </div>
            <div class='clear'></div>
        </div>
    </xsl:template>

    <xsl:template match="slp:details">
        <div class='sessionDetails'>
            <table class='fullWidth noMargin'>
                <colgroup>
                    <col style='width: 33%;'/>
                    <col style='width: 34%;'/>
                    <col style='width: 33%;'/>
                </colgroup>
                <tbody>
                    <tr>
                        <td class='leftAligned'>
                            <span class='bold largeText'>
                                Session
                                <xsl:value-of select="@id"/>
                            </span>
                        </td>
                        <td class='centered'>
                            <xsl:value-of select="slp:restriction"/>
                        </td>
                        <td class='rightAligned'>
                            <xsl:value-of select="slp:location"/>
                        </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td class='centered'>
                            <xsl:if test="slp:subRestrictions">
                                <span>
                                    <xsl:for-each select="slp:subRestrictions/slp:subRestriction">
                                        <xsl:value-of select="."/>
                                        <xsl:if test="position() != last()">
                                            <xsl:text>, </xsl:text>
                                        </xsl:if>
                                    </xsl:for-each>
                                </span>
                            </xsl:if>
                        </td>
                        <td class='rightAligned'>
                            <xsl:value-of select="slp:date"/>
                            <xsl:if test="slp:momentOfDay/text() != 'Unknown'">
                                <xsl:text>, </xsl:text>
                                <xsl:value-of select="slp:momentOfDay"/>
                            </xsl:if>
                        </td>
                    </tr>

                    <tr>
                        <td class='leftAligned'>
                            <span>
                                <xsl:text>Time: </xsl:text>
                                <xsl:choose>
                                    <xsl:when test="slp:duration">
                                        <xsl:value-of select="slp:duration"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:text>--</xsl:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <xsl:text> mins.</xsl:text>

                                <xsl:if test="slp:durationSideA or slp:durationSideB">
                                    <xsl:text> (</xsl:text>
                                    <xsl:if test="slp:durationSideA &gt; 0">
                                        <xsl:text>Side A: </xsl:text>
                                        <xsl:choose>
                                            <xsl:when test="slp:durationSideA">
                                                <xsl:value-of select="slp:durationSideA"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:text>--</xsl:text>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                        <xsl:text> mins.</xsl:text>
                                    </xsl:if>
                                    <xsl:text> - </xsl:text>
                                    <xsl:if test="slp:durationSideB &gt; 0">
                                        <xsl:text>Side B: </xsl:text>
                                        <xsl:choose>
                                            <xsl:when test="slp:durationSideB">
                                                <xsl:value-of select="slp:durationSideB"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:text>--</xsl:text>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                        <xsl:text> mins.</xsl:text>
                                    </xsl:if>
                                    <xsl:text>)</xsl:text>
                                </xsl:if>
                            </span>
                        </td>

                        <td></td>

                        <td class='rightAligned'>
                            <span>
                                Recommendation:
                                <xsl:value-of select="slp:recommendation"/>
                            </span>
                        </td>
                    </tr>
                    <xsl:apply-templates select="slp:keyTopics"/>
                    <xsl:apply-templates select="slp:mediaDetails[count(*) &gt; 0]"/>
                    <xsl:apply-templates select="slp:additionalRemarks"/>
                    <xsl:apply-templates select="slp:author"/>
                </tbody>
            </table>
        </div>
    </xsl:template>

    <xsl:template match="slp:keyTopics">
        <tr>
            <td colspan="3">
                <span class='bold'>Key Topics:</span>
                <xsl:for-each select="slp:keyTopic">
                    <span>
                        <xsl:value-of select="."/>
                        <xsl:if test="position() != last()">
                            <xsl:text>; </xsl:text>
                        </xsl:if>
                    </span>
                </xsl:for-each>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="slp:mediaDetails">
        <tr>
            <td colspan="3">
                <span class='bold'>Media:</span>
                <xsl:for-each select="*">
                    <span>
                        <xsl:apply-templates select="."/>
                        <xsl:if test="position() != last()">
                            <xsl:text>; </xsl:text>
                        </xsl:if>
                    </span>
                </xsl:for-each>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="slp:palVideoDetails">
        <xsl:text>PAL </xsl:text>
        <xsl:value-of select="@id"/>
        <xsl:if test="@restriction != ''">
            <xsl:text> (</xsl:text>
            <xsl:value-of select="@restriction"/>
            <xsl:text>)</xsl:text>
        </xsl:if>
    </xsl:template>

    <xsl:template match="slp:ntscVideoDetails">
        <xsl:text>NTSC </xsl:text>
        <xsl:value-of select="@id"/>
        <xsl:if test="@restriction != ''">
            <xsl:text> (</xsl:text>
            <xsl:value-of select="@restriction"/>
            <xsl:text>)</xsl:text>
        </xsl:if>
    </xsl:template>

    <xsl:template match="slp:genericVideoDetails">
        <xsl:text>Video </xsl:text>
        <xsl:value-of select="@id"/>
        <xsl:if test="@restriction != ''">
            <xsl:text> (</xsl:text>
            <xsl:value-of select="@restriction"/>
            <xsl:text>)</xsl:text>
        </xsl:if>
    </xsl:template>

    <xsl:template match="slp:additionalRemarks">
        <xsl:if test="slp:additionalRemark">
            <tr>
                <td colspan="3">
                    <span class='bold'>Additional Remarks:</span>
                    <span>
                        <xsl:for-each select="slp:additionalRemark">
                            <xsl:value-of select="."/>
                            <xsl:if test="position() != last()">
                                <xsl:text>; </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </span>
                </td>
            </tr>
        </xsl:if>
    </xsl:template>

    <xsl:template match="slp:author">
        <tr>
            <td colspan="3">
                <span class='bold'>Author:</span>
                <span>
                    <xsl:value-of select="text()"/>
                </span>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="slp:tracks">
        <xsl:apply-templates select="slp:track"/>
    </xsl:template>

    <xsl:template match="slp:track">
        <div class="track">
            <xsl:apply-templates select="slp:paragraph"/>
            <div class='paragraph'>
                <div class='leftColumn'>
                </div>
                <div class='duration rightColumn rightAligned'>
                    <xsl:if test="@duration &gt; 0">
                        <xsl:value-of select="@duration"/>
                        <xsl:text> mins.</xsl:text>
                    </xsl:if>
                </div>
                <div class='clear'></div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="slp:paragraph">
        <div class='paragraph'>
            <div class='leftColumn'>
                <div class="paragraphId">
                    <xsl:value-of select="@id"/>
                </div>
                <span class="label">
                    <xsl:value-of select="@paragraphLabel"/>
                </span>
            </div>
            <div>
                <xsl:attribute name="class">
                    <xsl:text>rightColumn </xsl:text>
                    <xsl:choose>
                        <xsl:when test="@alignment='left'">leftAligned</xsl:when>
                        <xsl:when test="@alignment='right'">rightAligned</xsl:when>
                        <xsl:when test="@alignment='center'">centered</xsl:when>
                        <xsl:when test="@alignment='justify'">justified</xsl:when>
                    </xsl:choose>
                </xsl:attribute>
                <xsl:element name="p">
                    <xsl:if test="@indent='true'">
                        <xsl:attribute name="class">indented</xsl:attribute>
                    </xsl:if>
                    <xsl:apply-templates select="@bulletLabel"/>
                    <xsl:apply-templates select="*"/>
                </xsl:element>
            </div>
            <div class='clear'></div>
        </div>
    </xsl:template>

    <xsl:template match="slp:paragraph[not(child::* | @paragraphLabel)]">
        <div class='paragraph'>
            <div class='leftColumn'>
                <div class="paragraphId">
                    <xsl:value-of select="@id"/>
                </div>
            </div>
            <div class="rightColumn leftAligned">
                <p class="empty"></p>
            </div>
            <div class='clear'></div>
        </div>
    </xsl:template>

    <xsl:template match="slp:paragraph/@bulletLabel">
        <xsl:element name="span">
            <xsl:attribute name="class">
                <xsl:text>bulletLabel</xsl:text>
            </xsl:attribute>
            <xsl:value-of select="current()"/>
            <xsl:text>.</xsl:text>
        </xsl:element>
    </xsl:template>

    <xsl:template match="slp:fragment">
        <xsl:element name="span">
            <xsl:attribute name="class">
                <xsl:value-of select="@type"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="@style"/>
                <xsl:text> fragment</xsl:text>
            </xsl:attribute>
            <xsl:value-of select="text()"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="slp:image">
        <xsl:element name="img">
            <xsl:attribute name="style">
                <xsl:text>width: </xsl:text>
                <xsl:value-of select="@widthMm"/>
                <xsl:text>mm</xsl:text>
                <xsl:text>; height: </xsl:text>
                <xsl:value-of select="@heightMm"/>
                <xsl:text>mm</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="src">
                <xsl:text>data:</xsl:text>
                <xsl:value-of select="@mimeType"/>
                <xsl:text>;base64,</xsl:text>
                <xsl:value-of select="text()"/>
            </xsl:attribute>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>