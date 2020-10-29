#include <stdio.h>
#include "student.h"

void inputStudents(Student * students[ ], int size) {
	for(int i = 0; i < size; i++){
		printf("Enter student %d's name:", i+1);
		scanf("%60s", students[i]->name);
		printf("%s's grade for CSharp:", students[i]->name);
		scanf("%lf", &students[i]->cSharp);
		printf("%s's grade for Math:", students[i]->name);
		scanf("%lf", &students[i]->math);
		printf("%s's grade for Systems:", students[i]->name);
		scanf("%lf", &students[i]->systems);
		
		students[i]->total = students[i]->cSharp + students[i]->math + students[i]->systems;
	}
}

void statsStudents(Student * students[ ], int size, double stats[ ]) {
	double avg = 0;
	double min = 300;
	double max = 0;
	for(int i = 0; i < size; i++){
		avg += students[i]->total;
		if(students[i]->total < min){
			min = students[i]->total;
		}
		if(students[i]->total > max){
			max = students[i]->total;
		}
	}
	avg /= size;
	stats[0] = min;
	stats[1] = max;
	stats[2] = avg;
}

void printStudents(Student * students[ ], int size, const double stats[ ]){
	printf("\n\n================================================\n\n");
	printf("No	CSharp		Math		Systems		Total		Name\n");
	for(int i = 0; i<size; i++){
		printf("%d	%.2lf		%.2lf		%.2lf		%.2lf		%s\n", i+1, students[i]->cSharp, students[i]->math, students[i]->systems, students[i]->total, students[i]->name);
	}
	
	printf("\n\nStatistics of the class:\n");
	printf("min = %.2lf, max = %.2lf, avg = %.2lf", stats[0], stats[1], stats[2]);
}

