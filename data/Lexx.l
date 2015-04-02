%{
	#include <string.h>
	#include "y.tab.h"

	int lineno = 1;
%}


%%


(?i:title)				{  return(TITLE); }
(?i:fields)				{  return(FIELD); }
(?i:buttons)			{  return(BUTTON); }
[a-zA-Z][a-zA-Z0-9_-]+	{  yylval.str_val = strdup(yytext); return(WORD); }


[=]                     {  return(EQUALS);      }
[{]                     {  return(LCURLY);     }
[}]                     {  return(RCURLY);     }
[;]                     {  return(SEMI);      }
[,]						{  return(COMMA); 	}
["]						{  return(QUOTE); 	}
(#!#)					{  return(SECTIONDELIM);	}


#(.)*                   { }
[ \t\b]               { }
[\n]					{ lineno++; }

%%