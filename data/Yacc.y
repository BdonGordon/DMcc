%{
    #include <stdlib.h>
    #include <stdio.h>
    #include <string.h>
    #include "ParameterManager.h"

    int PM_lexyParser(ParameterManager *p, FILE *fp);
    int yydebug = 1;
    extern int lineno; 
    char titleE[1000];
    char fieldsE[2100];
    char buttonsE[2000];
    char bottomEles[2000];
%}

%union {
    int int_val;
    float real_val;    
    char * str_val;    
}
%token EQUALS LCURLY RCURLY COMMA SECTIONDELIM
%token <str_val> TITLE FIELD BUTTON WORD SEMI QUOTE
%type <str_val> string list bottom
%%

file        :   top SECTIONDELIM bottom                 { strcpy(bottomEles, $3); printf("%s\n", bottomEles); }        
            ;

top         :   titleExp fieldExp buttonExp             {}
            ;

titleExp    :   TITLE EQUALS string SEMI               { strcpy(titleE, $3); printf("%s\n", titleE); }       
            ;

fieldExp    :   FIELD EQUALS list SEMI                  { strcpy(fieldsE, $3); printf("%s:\n", fieldsE); }
            ;

buttonExp   :   BUTTON EQUALS list SEMI                 { strcpy(buttonsE, $3); printf("%s:\n", buttonsE);}
            ;

string      :   QUOTE string                            { $$ = $2; }            
            |   WORD  string                            { strcat($1, " "); strcat($1, $2); }
            |   WORD QUOTE                              { $$ = $1; }
            ;

list        :   LCURLY list                            { $$ = $2; }
            |   string COMMA list                       { strcat($1, ", "); strcat($1, $3); }
            |   string RCURLY                        { $$ = $1; }
            ;

bottom      :   bottom WORD EQUALS string SEMI          { strcat($2, "="); strcat($2, $4); strcat($2, "\n"); strcat($1, $2); $$ = $1; }
            |   WORD EQUALS string SEMI                 { strcat($1, "="); strcat($1, $3); $$ = $1; }
            ;            

%%

extern FILE *yyin;

int yyerror(char *s)    { fprintf(stderr, "%s at %d\n", s, lineno); }

int main(){
    ParameterManager *p;
    char *name;
    char *itemP;
    char *itemB;
    char *fl;
    ParameterList *plist;
    ParameterList  *blist;
   
    if(!(p = PM_create(50))){
        exit(0);
    }

    PM_manage(p, "title", STRING_TYPE,1);
    PM_manage(p, "fields", LIST_TYPE,1);
    PM_manage(p, "buttons", LIST_TYPE,1);

    if (!PM_lexyParser(p, stdin)){
        exit(0);
    }

    name = PM_getValue(p,"title").str_val;

    plist = PM_getValue(p, "fields").list_val;
    blist = PM_getValue(p, "buttons").list_val;


    PM_destroy(p);

    return 1;
}

int PM_lexyParser(ParameterManager *p, FILE *fp){
    Boolean paramExist = false;    
    ParameterManager *temp = p;    
    char *fName;
    char *title;
    char *itemB;
    char *itemP;
    ParameterList *plist;
    ParameterList  *blist;

    yyin = fopen(fName, "r");
    if(yyin == NULL){
        yyin = stdin;
    }

    while(!feof(yyin)){
        yyparse();
        if(paramExist = foundParam(&p, "title")){
            //printf("Founder \n");
            p->assignedValue = true;
            parseStoreValue(STRING_TYPE, &p->v, titleE);
            p = temp;
        }
        else{
            printf("Error: No title.\n");
        }        

        if(paramExist = foundParam(&p, "fields")){
            //printf("fields here");
            p->assignedValue = true;
            parseStoreValue(LIST_TYPE, &p->v, fieldsE);
            p = temp;
        }
        else if(paramExist = foundParam(&p, "buttons") && buttonsE == " "){
            printf("Error: No fields.\n");
        }
        }

        if(paramExist = foundParam(&p, "buttons")){
            //printf("buttons here");
            p->assignedValue = true;
            parseStoreValue(LIST_TYPE, &p->v, buttonsE);
            p = temp;
        }
        else if(paramExist = foundParam(&p, "buttons") && buttonsE == " ") {
            printf("Error: No buttons.\n");
        }    
    

    printf("Here: %s\n", PM_getValue(p,"title").str_val);
    
    printf("Fields:\n");
    while(itemB = PL_next(PM_getValue(p, "fields").list_val)){
        printf("%s", itemB);
    }
    printf("\n");

    printf("Buttons:\n");
    while(itemP = PL_next(PM_getValue(p, "buttons").list_val)){
        printf("%s", itemP);
    }
    printf("\n");

    fclose(yyin);
    
    return 0;
}