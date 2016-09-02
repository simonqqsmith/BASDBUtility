# BASDBUtility
## Author
Simon Smith
## Creation Date
02 Sep 2016

## Introduction
The BAS Database Utility application simplifies database administration and is targeted to make specific tasks easy to carry out for any user with appropriate privileges.

## Installation
Download and install the package in the desired directory on the database host.
Configure the values in config.properties to match the target system.

## Compatibility
The BAS Database Utility has been developed and tested on Windows 10 for Oracle 11g and 12c using Java 8. However the code should be portable to any Microsoft Windows environment and any Oracle release since 8i.
Future releases will be compatible with Linux and Unix.

## Usage
After ensuring that the config.properties is correct, run BASDBUtility.jar to lauch the GUI. You will be prompted to confirm that configuration has been checked each time you launch the application - this is important.
The GUI has a button to execute each feature currently available, simply click the button and follow the prompts (there are very few prompts).

## Features
The following features are currently available for Oracle database:
* Hot Backup using RMAN
* Database refresh using RMAN
* Cold Backup using RMAN
* Database restore using RMAN

### Hot Backup using RMAN
Creates an RMAN backup of the database to local disk.
*The database must be in ARCHIVELOG mode

### Database refresh using RMAN
Refreshes an existing database from a backup to a point in time. This can also rename the database so can be used to refresh a test database from a production backup. 
*The backup must be copied to a local disk

### Cold Backup using RMAN
Creates an RMAN backup of the database to local disk.
*The database does not need to be in ARCHIVELOG mode

### Database restore using RMAN
Restores the database from an RMAN backup.
*No database renaming or point in time recovery.

## Example scenarios
Using the hot backup on production and refresh in non-prod is a useful way of resetting your environments in sync with current production.
When a database has been refreshed or is at a known start point, use a cold backup to keep as a snapshot so that you can use the restore feature to reset the data.