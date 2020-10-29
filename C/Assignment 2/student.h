typedef struct {
	char name[60];
	double cSharp, math, systems;
	double total;
}Student;

void inputStudents(Student * students[ ], int size);
void statsStudents(Student * students[ ], int size, double stats[ ]);
void printStudents(Student * students[ ], int size, const double stats[ ]);
