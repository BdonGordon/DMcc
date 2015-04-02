/*---------------------------------------------------------------------
 *                      ParameterHelper.c
 * Name: Brandon Gordon
 * Student ID: 0850874
 * Course: CIS2750 [W15]
 * Professor: David McCaughan 
 *---------------------------------------------------------------------
 */
#include "ParameterHelper.h"


int parseComment(FILE * fp){
    char c;
    
    while((c = fgetc(fp)) != '\n')
        if (c == EOF) return 0;
    return 1;
}


/* Double point p so that on return p will be pointed to the correct node */
Boolean foundParam(ParameterManager ** p, char * pname){
    if((*p) != NULL){
        while ((*p)->name[0] != '\0'){
            if (strcmp((*p)->name, pname) == 0){
                return true;
            }

            (*p) = (*p)->next;
        } 
    }
    return false; 
}

/*Simple cleaner way of storing values of the buffer into the UNION types*/
int parseStoreValue(int type, union param_value * val, char * buffer){
    char * ptr = NULL; /* Used in strtol */
    
    switch (type){
        case INT_TYPE:  
            (*val).int_val = (int)strtol(buffer, &ptr, 10);
            break;

        case REAL_TYPE: 
            (*val).real_val = strtof(buffer, &ptr); 
            break;

        case BOOLEAN_TYPE: 
            (*val).bool_val = (strcasecmp(buffer, "true") == 0) ? true : false;
            break;

        case STRING_TYPE:
            strcpy((*val).str_val, buffer);
            break;

        case LIST_TYPE:
            (*val).list_val = storeListVals(buffer, ",");        
            break;
    }
    
    
    return 1;
}


int checkBufferType(int type, char * buffer){
    int i = 0;
    
    if (type == -1){
        /* Check if type is integer */
        for (i = 0; i < sizeof(buffer); i++) {
            if (isdigit(buffer[i])){
                return INT_TYPE;
            }
            else{
                type = -1;
            }
        }        
        /* Check if type is boolean; capital or lower case is fine */
        if (strcasecmp(buffer, "true") == 0 || strcasecmp(buffer, "false") == 0){
            return BOOLEAN_TYPE;
        }
    }
    return type;
}


ParameterList * storeListVals(char * buffer, char * delimiter){
    ParameterList *temp; /*always point to head of the list*/
    ParameterList *pL;
    char * token;
    
    pL = calloc(1,sizeof(ParameterList));
    temp = pL;
    token = strtok(buffer, delimiter);
    while (token != NULL) {
        pL->value = calloc(STR_SIZE, sizeof(char));
        pL->next = calloc(1, sizeof(ParameterList));
        strcpy(pL->value, token);                

        pL = pL->next;
        token = strtok(NULL, delimiter);
    }
    
    return temp;
}

int requiredCheck(ParameterManager *p){
    while(p != NULL){
        if(p->required == 1 && p->assignedValue == false){
            printf("Error: A required parameter is missing the corresponding value.\n");
            return 0;
        }
        p = p->next;
    }

    return 1;
}


