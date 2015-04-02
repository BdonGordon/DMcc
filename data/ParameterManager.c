/*---------------------------------------------------------------------
 *                      ParameterManager.c
 * Name: Brandon Gordon
 * Student ID: 0850874
 * Course: CIS2750 [W15]
 * Professor: David McCaughan 
 *---------------------------------------------------------------------
 */
#include <jni.h>
#include "ParameterManager.h"
/*Have to include these functions in separately since, after compilation, these will
*functions will be declared as an implicit declaration of these functions.
*If attempted to inlude "ParameterHelper.h", there will be multiple errors
*after compilation
*/
int parseComment(FILE * fp);
Boolean foundParam(ParameterManager ** p, char * pname);
int parseStoreValue(int type, union param_value * val, char * buffer);
int checkBufferType(int type, char * buffer);
int requiredCheck(ParameterManager *p);

/* Creates a new parameter manager object
 *  PRE: size is a positive integer
 *  POST: Returns a new parameter manager object initialized 
 *      to be empty (i.e. managing no parameters) on success, 
 *      NULL otherwise (memory allocation failure)
 */
ParameterManager * PM_create(int size){
    ParameterManager * head = NULL;
    
    if (size < 0) {
        printf("Error: Size must be >= 0\n");
    }
    else if (size >= 1) {
        head = calloc(1, sizeof(ParameterManager));
        head->size = size;
        head->required = 0;
        head->assignedValue = false;
        head->name = calloc(STR_SIZE, sizeof(char));
        head->v_type = INT_TYPE;
        /* Only need to initialize the largest member of union */
        head->v.list_val = calloc(LIST_SIZE, sizeof(ParameterList));
        head->next = NULL;
    }
    
    return head;
}


/* Destroys a parameter manager object
 *  PRE: n/a
 *  POST: all memory associated with parameter manager p is freed; 
 *      returns 1 on success, 0 otherwise
 */
int PM_destroy(ParameterManager *p){
    ParameterManager * temp;

    while(p != NULL){
        temp = p;
        p = p->next;
        free(temp->name);
        free(temp);
    }
    p = NULL;
    
    return 1;
}


/* Extract values for parameters from an input stream 
 *  PRE: fp is a valid input stream ready for reading that contains 
 *      the desired parameters
 *  POST: All required parameters, and those optional parameters 
 *      present, are assigned values that are consumed from fp, 
 *      respecting comment as a "start of comment to end of line" 
 *      character if not nul ('\0'); returns non-zero on success, 0 
 *      otherwise (parse error,memory allocation failure)
 */
int PM_parseFrom(ParameterManager *p, FILE *fp, char comment){
    ParameterManager * temp = p;
    char buffer[STR_SIZE]; 
    char c;
    int pType;
    int letter = 0;
    int count = 0;
    int i = 0;
    int size = p->size;
    Boolean stringNow = false;
    Boolean paramExist = false;
    
    memset(buffer, 0, sizeof(buffer));
    while ((c = fgetc(fp)) != EOF){   
        /* Read From Comment to newline */        

        if (c == comment){   
            parseComment(fp);
        }
        /* Finished Reading pname, find it in param Manager */
        else if (c == '='){ 
            /* Finished Reading pname, find it in param Manager */
            paramExist = foundParam(&p, buffer);
            /* Clear Buffer */
            memset(buffer, 0, sizeof(buffer));
            letter = 0;
        }
        else if (c == '{'){ 
            pType = LIST_TYPE;
        }
        else if (c == '}'){
            pType = LIST_TYPE;
        }
        else if (c == '"' && stringNow == true){
            stringNow = false;
        }
        else if (c == '"') {
            pType = STRING_TYPE;
            stringNow = true;
        }
        else if (c == '.') {
            pType = REAL_TYPE;
            buffer[letter] = c; 
            letter++;
        }
        else if (c == ';'){
            if (paramExist){
                pType = checkBufferType(pType, buffer);
                if (pType == p->v_type){
                    p->assignedValue = true;
                    parseStoreValue(pType, &p->v, buffer); 
                    count++;
                }
                /*Error check to see that user specifies enough create for the number of parameters*/
                if (count+1 > size){
                    printf("Error: More parameters than allocated for.\n");
                    exit(-1);
                }
                paramExist = false;
            }
            pType = -1;                         /* Reset pType */
            p = temp;                           /* Reset p to head */
            memset(buffer, 0, sizeof(buffer));  /* Clear Buffer */
            letter = 0;                         /* Reset Buffer index*/
        }
        /*if there is a space in the parameter name we want to save it*/
        else if (isspace(c) && stringNow == true){
            /* Save whitespace in string */
            buffer[letter] = c; 
            letter++;
        }
        else if (isspace(c) && stringNow == false){
            /* Don't save white space when it's not in a string; do nothing */
        }
        /*if the character is printable*/
        else if (isprint(c)){ 
            /* Save every other character to buffer */
            buffer[letter] = c; 
            letter++; 
        }
        else return 0; /* Read in invalid character */
    }

    return requiredCheck(p);

}


/* Register parameter for management
 *  PRE: pname does not duplicate the name of a parameter already 
 *      managed
 *  POST: Parameter named pname of type ptype (see note 3 below) is 
 *      registered with p as a parameter; if required is zero the 
 *      parameter will be considered optional, otherwise it will be 
 *      considered required; returns 1 on success, 0 otherwise 
 *      (duplicate name, memory allocation failure)
 */
int PM_manage(ParameterManager *p, char *pname, param_t ptype, int required){
    ParameterManager * temp;
    
    temp = p;
    /* Check if pname is a duplicate */
    while (temp->name[0] != '\0') 
    {
        if (strcasecmp(temp->name, pname) == 0){ 
            printf("Parameter name \"%s\" is duplicated.\n", temp->name);
            return 0;
        }
        temp = temp->next;
    }
    
    /* Add to end of list, which is where temp is currently */
    strcpy(temp->name, pname);
    temp->required = required;
    temp->v_type = ptype;
    
    /* Initialize next parameter and check memory fail */
    if ((temp->next = PM_create(p->size)) == NULL){ 
        return 0; 
    }

    return 1;
}


/* Test if a parameter has been assigned a value
 *  PRE: pname is currently managed by p
 *  POST: Returns 1 if pname has been assigned a value, 0 otherwise (no 
 *      value, unknown parameter)
 */
int PM_hasValue(ParameterManager *p, char *pname){
    /* find pname or don't */
    while (p->name[0] == '\0' || strcmp(p->name, pname) != 0) 
        p = p->next;
    
    /* pname doesn't exist */
    if (p->name[0] == '\0' ){    
        return 0;
    } 
    /* pname has value */
    else if (p->assignedValue){  
        return 1; 
    }
    /* pname doesn't have value */
    else{                        
        return 0; 
    }
}

char * PL_next(ParameterList * l)
{
    char * val;

    if (l == NULL){
        return NULL;    
    }
    
    val = l->value;
    if(l->next == NULL){
        return NULL;
    }
    *l = *l->next;    

    return val;
}

/* Obtain the value assigned to pname
 *  PRE: pname is currently managed by p and has been assigned a value
 *  POST: Returns the value (see note 4 below) assigned to pname; 
 *      result is undefined if pname has not been assigned a value or 
 *      is unknown
 */
union param_value PM_getValue(ParameterManager *p, char *pname){
    while (strcmp(p->name, pname) != 0) {
        if (p == NULL) {
            printf("Error: \"%s\" is not managed.", pname); 
            exit(-1);
        }
        p = p->next;
    }    
    if (p != NULL)
        return p->v;
    else
    {
        printf("Error: pname is not managed by p.\n");
        exit(-1);
    }

       
            
            
}




/*gcc -fPIC -o libJavaPMManger.so -lc -shared ParameterManager.c 
*ParameterHelper.c  -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include 
*-I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/linux
*/

JNIEXPORT jstring JNICALL Java_Dialogc_parseConfig(JNIEnv *env, jclass obj, jstring configTit, jstring pName, jint pType, jint parseMode){
	char *tempFile;
    char *tempFileY;
	char *pCName;
    char *pCNameY;
	char values[1025] = "";
    char valuesY[1025] = "";
	char *item;
    char *itemY;
	ParameterList *ls;
    ParameterList *lsY;
	ParameterManager *p;
    ParameterManager *pY;
	jstring retString;
    jstring retStringY;
	FILE *fp;
    FILE *fpY;
    parseMode = 0;

    if(parseMode == 0){
    	/*Convert jstrings into strings that are compatible with C for the .CONFIG TITLE*/
    	tempFile = (char *)(*env)->GetStringUTFChars(env, configTit, NULL);
    	fp = fopen(tempFile, "r");
    	
    	/*Convert jstrings into strings that are compatible with C for the PARAMETER NAME*/
    	pCName = (char *)(*env)->GetStringUTFChars(env, pName, NULL);
    	
    	/*PM_create for either title, buttons, or fields*/
    	/*printf(":%s & :%s:\n", tempFile, pCName);*/
    	p = PM_create(10);
    	PM_manage(p, pCName, pType, 1);
    	PM_parseFrom(p, fp, '#');

    	if(pType == 3){
    		strcpy(values, PM_getValue(p, pCName).str_val);	
    	}
    	else if(pType == 4){
    		ls = PM_getValue(p, pCName).list_val;
    		while(ls->next != NULL){	        
    			strcat(values, ls->value);
    			strcat(values, ",");
                ls = ls->next;    
    		}		
    	}
    	
    	/*printf("val: %s\n", PM_getValue(p, pCName).str_val);*/
    	PM_destroy(p);

    	(*env)->ReleaseStringUTFChars(env, pName, pCName);
    	(*env)->ReleaseStringUTFChars(env, configTit, tempFile);
    	retString = (*env)->NewStringUTF(env, values);
    }

    if(parseMode == 1){
        tempFileY = (char *)(*env)->GetStringUTFChars(env, configTit, NULL);
        fpY = fopen(tempFileY, "r");

        pCNameY = (char *)(*env)->GetStringUTFChars(env, pName, NULL);
        pY = PM_create(50);
        PM_manage(pY, pCNameY, pType, 1);
        PM_lexyParser(pY, fpY);

        if(pType == 3){
            strcpy(valuesY, PM_getValue(pY, pCNameY).str_val); 
        }
        else if(pType == 4){
            lsY = PM_getValue(pY, pCNameY).list_val;
            while(lsY->next != NULL){
                strcat(valuesY, lsY->value);
                strcat(valuesY, ",");
                lsY = lsY->next;
            }
        }

        PM_destroy(p);

        (*env)->ReleaseStringUTFChars(env, pName, pCNameY);
        (*env)->ReleaseStringUTFChars(env, configTit, tempFileY);
        retStringY = (*env)->NewStringUTF(env, valuesY);

    }

	return retString;
}





















