#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "student.h"


int main(int argc, char *argv[]) {
	int size;
	double stats[3];
	start:
	printf("Insert the number of students:");
	int check = scanf("%d", &size);
	if (check != 1 || size < 2){
		printf("Please enter a number bigger than 1.\n");
		fflush(stdin);
		goto start;
	}
	Student **students =  malloc(size*sizeof(Student));
	for(int i = 0; i<size;i++){
		students[i] =  malloc(sizeof(Student));
	}
	inputStudents(students, size);	
	statsStudents(students, size, stats);
	printStudents(students, size, stats);
	free(students);
	return 0;
}
