psql -U postgres -d db_coursework -a -f ./drop_tables.sql
psql -U postgres -d db_coursework -a -f ./drop_types.sql
psql -U postgres -d db_coursework -a -f ./model.sql
psql -U postgres -d db_coursework -a -f ./functions.sql
psql -U postgres -d db_coursework -a -f ./triggers.sql
psql -U postgres -d db_coursework -a -f ./insertions.sql
