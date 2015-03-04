

## Introduction

Simple example that retrieves code from two tables and joins them based on the predicate column personnel_id.
The organisation table is filtered to only include those people who are marked as executives.

### Executing
In order to run from the command line enter sbt i.e.

	$ sbt
   
   
Then once in sbt run:

	> clean
   
   	> compile
   
   	> run [hostname/ip of C*]
   
If you don't specify the parameter for the hostname it will default to 127.0.0.1

Given the correct dataset matching entries will be printed on stdout like this:

	Executive -> Personnel ID = 2, Name = Jonathan Ellis
	Executive -> Personnel ID = 3, Name = Matt Pfeil
	Executive -> Personnel ID = 4, Name = Robin Schumacher
	Executive -> Personnel ID = 5, Name = Martin Van Ryswyk
	Executive -> Personnel ID = 6, Name = John Schweitzer
	Executive -> Personnel ID = 7, Name = Tony Kavanagh
	Executive -> Personnel ID = 8, Name = Debbie Murray
	Executive -> Personnel ID = 9, Name = Clint Smith
