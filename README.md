FHL Teachings Archives
============
Copyright 2013-2019 Mark Koops

This Java project aims to parse a library of Word documents into structured files in XML format.
I have started the software project in 2013 as a voluntary service to my spiritual school 
[Foundation of Higher Learning](https://www.thefhl.org) and my spiritual teacher Imre Vallyon. 
Since then I've been contributing to this project in my spare hours. So far I'm the sole committer on this code base, 
all code is written by me. 

The library of Word documents consists of the transcripts of over four thousand spiritual teachings sessions lead by my 
teacher. The sessions and transcripts date back to the 80s till today, a period of over 30 years.

The Word documents have been written according a pretty consistent but evolving layout. 
My main purpose of the parser code is to recover all the semantics expressed in the layout besides the literal text itself.

An additional point of concern is that a large part of the library have been written in old versions of Word for Mac, 
even in the pre-Unicode and pre-TrueType font era. The transcripts include extended characters as used in historical sacred languages 
like old Sanskrit, old Hebrew and old Greek. For that purpose the school applied a custom font type.
The parser aims to translate all extended characters into current Unicode code points.

Altogether the library of Word documents have served the school for many years but the aim of the parser is to 
transform the transcripts into an XML format that will allow us to open up the possibilities of current IT, like dynamic 
rendering, indexing of the teachings, linking teachings to each other, etc, etc.
  
Note: This code base is an excerpt of a larger private repository, that besides the code also involves the teaching 
transcripts themselves. For those materials it is not to me to publish them.


Any more info: info@markkoops.nl 
   