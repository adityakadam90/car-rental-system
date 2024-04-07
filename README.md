# car-rental-system
first jdbc project
<br></br>
i am first time intreact with database. so many mistakes i faced but finally done.....
<br> </br>
database requires tables : 1.car_info   2.user_info <br></br>
mysql> describe car_info;<br></br>
+-------------+------------+------+-----+---------+-------+
| Field       | Type       | Null | Key | Default | Extra |<br></br>
+-------------+------------+------+-----+---------+-------+<br></br>
| car_id      | varchar(6) | NO   | PRI | NULL    |       |<br></br>
| car_name    | varchar(6) | NO   |     | NULL    |       |<br></br>
| available   | tinyint(1) | YES  |     | 0       |       |<br></br>
| perdayprice | int        | NO   |     | NULL    |       |<br></br>
+-------------+------------+------+-----+---------+-------+
<br></br>
mysql> describe user_info;<br></br>
+---------+-------------+------+-----+---------+----------------+
| Field   | Type        | Null | Key | Default | Extra          |<br></br>
+---------+-------------+------+-----+---------+----------------+<br></br>
| name    | varchar(20) | NO   |     | NULL    |                |<br></br>
| user_id | int         | NO   | PRI | NULL    | auto_increment |<br></br>
| id_car  | varchar(7)  | YES  | MUL | NULL    |                |<br></br>
+---------+-------------+------+-----+---------+----------------+<br></br>
