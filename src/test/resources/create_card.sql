select * from cards;
select * from card_matches;

explain;
select id as card_id, promotion, name, date
from cards
order by date desc
limit 0, 5;

explain;
select p.match_id, p.participant, m.winner
from card_match_participants p
left join card_matches m on p.card_id = m.card_id and p.match_id = m.match_id
where p.card_id='WWENOC15'
order by match_id;

--

delete from card_match_participants where card_id = 'WWEHIC15';
delete from card_matches where card_id = 'WWEHIC15';
delete from cards where id = 'WWEHIC15';

-- 

-- WWE Hell in a Cell 2015 
insert into cards (id, promotion, name, date)
values ('WWEHIC15', 'WWE', 'Hell in a Cell', STR_TO_DATE('10/25/2015', '%m/%d/%Y'));

insert into card_matches (card_id, match_id) values ('WWEHIC15', 1);
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 1, 'The Undertaker');
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 1, 'Brock Lesnar');

insert into card_matches (card_id, match_id) values ('WWEHIC15', 2);
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 2, 'Roman Reigns');
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 2, 'Bray Wyatt');

insert into card_matches (card_id, match_id) values ('WWEHIC15', 3);
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 3, 'Seth Rollins');
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 3, 'Demon Kane');

insert into card_matches (card_id, match_id) values ('WWEHIC15', 4);
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 4, 'Charlotte');
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 4, 'Nikki Bella');

insert into card_matches (card_id, match_id) values ('WWEHIC15', 5);
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 5, 'The New Day');
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 5, 'The Dudley Boyz');

insert into card_matches (card_id, match_id) values ('WWEHIC15', 6);
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 6, 'Kevin Owens');
insert into card_match_participants (card_id, match_id, participant) values ('WWEHIC15', 6, 'Ryback');




-- WWE Night of Champions 2015 
insert into cards (id, promotion, name, date)
values ('WWENOC15', 'WWE', 'Night of Champions', STR_TO_DATE('9/20/2015', '%m/%d/%Y'));

insert into card_matches (card_id, match_id) values ('WWENOC15', 1);
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 1, 'Sting');
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 1, 'Seth Rollins');

insert into card_matches (card_id, match_id) values ('WWENOC15', 2);
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 2, 'John Cena');
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 2, 'Seth Rollins');

insert into card_matches (card_id, match_id) values ('WWENOC15', 3);
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 3, 'Kevin Owens');
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 3, 'Ryback');

insert into card_matches (card_id, match_id) values ('WWENOC15', 4);
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 4, 'Charlotte');
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 4, 'Nikki Bella');

insert into card_matches (card_id, match_id) values ('WWENOC15', 5);
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 5, 'The Wyatt Family');
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 5, 'Reigns, Ambrose, Jericho');

insert into card_matches (card_id, match_id) values ('WWENOC15', 6);
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 6, 'Dolph Ziggler');
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 6, 'Rusev');

insert into card_matches (card_id, match_id) values ('WWENOC15', 7);
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 7, 'The Dudley Boyz');
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 7, 'The New Day');

insert into card_matches (card_id, match_id) values ('WWENOC15', 8);
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 8, 'Cosmic Wasteland');
insert into card_match_participants (card_id, match_id, participant) values ('WWENOC15', 8, 'Neville & Lucha Dragons');


select * from card_matches where card_id = 'WWENOC15';
update card_matches set winner = 'Seth Rollins' where card_id = 'WWENOC15' and match_id = 1;
update card_matches set winner = 'John Cena' where card_id = 'WWENOC15' and match_id = 2;
update card_matches set winner = 'Kevin Owens' where card_id = 'WWENOC15' and match_id = 3;
update card_matches set winner = 'Charlotte' where card_id = 'WWENOC15' and match_id = 4;
update card_matches set winner = 'The Wyatt Family' where card_id = 'WWENOC15' and match_id = 5;
update card_matches set winner = 'Dolph Ziggler' where card_id = 'WWENOC15' and match_id = 6;
update card_matches set winner = 'The Dudley Boyz' where card_id = 'WWENOC15' and match_id = 7;
update card_matches set winner = 'Cosmic Wasteland' where card_id = 'WWENOC15' and match_id = 8;