

## Introduction

Simple example that retrieves code from two tables and joins them based on the predicate column personnel_id.
The organisation table is filtered to only include those people who are marked as executives.

### Executing
In order to run from the command line enter sbt i.e.

	$ sbt
   
   
Then once in sbt run:

	> clean
   
   	> compile
   
   	> run [sparkmaster=???] [cassandrahost=???]
   	
   	
You'll then be prompted to select the example you want to execute..    
    
    Multiple main classes detected, select one to run:

        [1] com.datastax.demo.JoinTablesExampleApp
        [2] com.datastax.demo.JoinAndSaveToTableExampleApp

    Enter number:
   	
Enter the corresponding number and hit return.
   
If you don't specify the parameter for sparkmaster then it will default to local. If you don't specify the cassandrahost then it will be assumed to be 127.0.0.1 

When executing `JoinTablesExampleApp`, given the correct dataset matching entries will be printed on stdout like this:

	Executive -> Personnel ID = 2, Name = Jonathan Ellis
	Executive -> Personnel ID = 3, Name = Matt Pfeil
	Executive -> Personnel ID = 4, Name = Robin Schumacher
	Executive -> Personnel ID = 5, Name = Martin Van Ryswyk
	Executive -> Personnel ID = 6, Name = John Schweitzer
	Executive -> Personnel ID = 7, Name = Tony Kavanagh
	Executive -> Personnel ID = 8, Name = Debbie Murray
	Executive -> Personnel ID = 9, Name = Clint Smith

If you choose `JoinAndSaveToTableExampleApp` then you can check the output by connecting to cassandra and selecting from the `datastax.org_directory` table:

    select * from datastax.org_directory
    
You should see the following output:

    Connected to SparkCluster at 192.168.59.104:9160.
	[cqlsh 4.1.1 | Cassandra 2.0.11.83 | DSE 4.6.0 | CQL spec 3.1.1 | Thrift protocol 19.39.0]
	Use HELP for help.
	cqlsh> select * from datastax.org_directory;

	 personnel_id | fname    | lname      | role
	--------------+----------+------------+-------------------------------
    	        5 |   Martin | Van Ryswyk |                Executive Team
	           13 |   Hayato |    Shimizu |           Customer Operations
    	       11 |  Tupshin |     Harper |              Field Operations
        	    8 |   Debbie |     Murray |                Executive Team
	            2 | Jonathan |      Ellis |                Executive Team
    	        4 |    Robin | Schumacher |                Executive Team
        	   15 |   Johnny |     Miller | EMEA Solutions Architect Team
	            7 |     Tony |   Kavanagh |                Executive Team
    	        6 |     John | Schweitzer |                Executive Team
        	    9 |    Clint |      Smith |                Executive Team
	           14 |     Matt |      Stump |           Customer Operations
    	       12 |      Amy |      McNee |              Field Operations
        	    3 |     Matt |      Pfeil |                Executive Team

	(13 rows)

	cqlsh>
	
	
If not then check the debug output from the application for errors.