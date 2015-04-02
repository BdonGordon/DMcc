/*---------------------------------------------------------------------
 *                      ParameterHelper.h
 * Name: Brandon Gordon
 * Student ID: 0850874
 * Course: CIS2750 [W15]
 * Professor: David McCaughan 
 *---------------------------------------------------------------------
 */

#include "ParameterManager.h"

/*
 * Reads fp until next '\n' or EOF
 * Return:  1 for success
 *          0 for failure
 */
int parseComment(FILE * fp);

/*
 * Reads through p checking if pname is equal to any p->name. On exit, 
 *      p will be at the node with p->name = paramName or at the last 
 *      node. 
 * Return:  
 *      true if pname is in p
 *      false otherwise
 */
Boolean foundParam(ParameterManager ** p, char * pname);

/*
 * Takes value from buffer and sets val to the corresponding union type
 * Return:  1 for success
 *          0 for failure
 */
int parseStoreValue(int type, union param_value * val, char * buffer);
int checkBufferType(int type, char * buffer);
ParameterList * storeListVals(char * buffer, char * delimiter);
int requiredCheck(ParameterManager *p);




