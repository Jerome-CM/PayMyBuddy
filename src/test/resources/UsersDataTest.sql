insert into users(mail,password,account_balance,date_creation)values("me@paymybuddy.com", "$2a$10$FzNJQrIguYNMPrsbj6u29.tAiZWj5QdPcDrxPMP1A/rSxLkPRWyvi",200,NOW());
insert into users(mail,password,account_balance,date_creation)values("contact@paymybuddy.com", "$2a$10$nO/6hXXJWIcTJA6tFS9zH.tw.fAGljlso6pVQMl9DNCeKt83JDJmi",0,NOW());
insert into users(mail,password,account_balance,date_creation)values("home@paymybuddy.com", "$2a$10$aqWEIE2JslRWczcYe1igQuYG/bepVvbPAJXo4N/ra4ArazBkY8jeS",0,NOW());
insert into users(mail,password,account_balance,date_creation)values("transfer@paymybuddy.com", "$2a$10$NyQKbzhoMJ95y214mIsAvOY.fy8XEbc1K0As0reffRt8.SUEhROju",0,NOW());
insert into users(mail,password,account_balance,date_creation,firstname,lastname)values("profile@paymybuddy.com", "$2a$10$39nOUuqYo/M.PYXUN1UUHuyTJ23yTDDX2zNdXFSbkoAkFzm4/jSnq",20,NOW(),"Adrien","Vier");

insert into users_friends(user_id,friends_id) values (1,5);
insert into users_friends(user_id,friends_id) values (5,1);