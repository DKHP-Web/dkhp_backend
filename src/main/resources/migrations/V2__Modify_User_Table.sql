alter table user drop column is_actived;
alter table user add column is_temp_password bit;
alter table user add column refresh_token varchar(255);
alter table user add column temp_password_token varchar(255);