/*---------------------------------------------------------------------
 *                      ParameterManager.h
 * Name: Brandon Gordon
 * Student ID: 0850874
 * Course: CIS2750 [W15]
 * Professor: David McCaughan 
 *---------------------------------------------------------------------
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <strings.h>

#define STR_SIZE 200
#define LIST_SIZE 100

typedef enum{ 
    false, true 
}Boolean;

enum types{ 
    INT_TYPE, REAL_TYPE, BOOLEAN_TYPE, STRING_TYPE, LIST_TYPE
};
typedef enum types param_t;

struct ParameterList{
    char * value;
    struct ParameterList * next;
};
typedef struct ParameterList ParameterList;

union param_value{
    int int_val;
    float real_val;
    Boolean bool_val;
    char * str_val;
    ParameterList * list_val;
};

struct ParameterManager {
    int size;
    int required;
    Boolean assignedValue; 
    char * name;
    param_t v_type;
    union param_value v;
    struct ParameterManager * next;
};
typedef struct ParameterManager ParameterManager; 

ParameterManager * PM_create(int size);
int PM_destroy(ParameterManager *p);
int PM_parseFrom(ParameterManager *p, FILE *fp, char comment);
int PM_manage(ParameterManager *p, char *pname, param_t ptype, int required);
int PM_hasValue(ParameterManager *p, char *pname);
char * PL_next(ParameterList * l);
union param_value PM_getValue(ParameterManager *p, char *pname);



