--테이블 삭제
drop table member;
drop table uploadfile;
drop table code;
drop table product;

--시퀀스삭제
drop sequence member_member_id_seq;
drop sequence code_code_id_seq;
drop sequence uploadfile_uploadfile_id_seq;
drop sequence product_product_id_seq;
-------
--코드
-------
create table code(
    code_id     varchar2(11),       --코드
    decode      varchar2(30),       --코드명
    discript    clob,               --코드설명
    pcode_id    varchar2(11),       --상위코드
    useyn       char(1) default 'Y',            --사용여부 (사용:'Y',미사용:'N')
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);
--기본키
alter table code add Constraint code_code_id_pk primary key (code_id);

--외래키
alter table code add constraint bbs_pcode_id_fk
    foreign key(pcode_id) references code(code_id);

--제약조건
alter table code modify decode constraint code_decode_nn not null;
alter table code modify useyn constraint code_useyn_nn not null;
alter table code add constraint code_useyn_ck check(useyn in ('Y','N'));

--시퀀스
create sequence code_code_id_seq;

--샘플데이터 of code
--insert into code (code_id,decode,pcode_id,useyn) values ('B01','게시판',null,'Y');
--insert into code (code_id,decode,pcode_id,useyn) values ('B0101','Spring','B01','Y');
--insert into code (code_id,decode,pcode_id,useyn) values ('B0102','Datbase','B01','Y');
--insert into code (code_id,decode,pcode_id,useyn) values ('B0103','Q_A','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01','회원구분',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0101','일반','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0102','우수','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A1','관리자1','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A2','관리자2','M01','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('A02','지역',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0201','서울','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0202','부산','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0203','대구','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0204','울산','A02','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('F01','첨부파일',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0101','회원','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F010101','사진','F0101','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0102','게시판','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F010201','첨부파일','F0102','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0103','상품관리','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F010301','설명서','F0103','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F010302','이미지','F0103','Y');


commit;

-------
--회원
-------
create table member (
    member_id   number,         --내부 관리 아이디
    email       varchar2(50),   --로긴 아이디
    passwd      varchar2(12),   --로긴 비밀번호
    tel         varchar2(13),   --연락처 ex)010-1234-5678
    nickname    varchar2(30),   --별칭
    gender      varchar2(6),    --성별
    hobby       varchar2(300),  --취미
    region      varchar2(11),   --지역
    gubun       varchar2(11)   default 'M0101', --회원구분 (일반,우수,관리자..)
    pic         blob,            --사진
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);
--기본키생성
alter table member add Constraint member_member_id_pk primary key (member_id);

--외래키
alter table member add constraint member_region_fk
    foreign key(region) references code(code_id);
alter table member add constraint member_gubun_fk
    foreign key(gubun) references code(code_id);

--제약조건
alter table member modify email constraint member_email_uk unique;
alter table member modify email constraint member_email_nn not null;
alter table member add constraint member_gender_ck check (gender in ('남자','여자'));

--시퀀스
create sequence member_member_id_seq;

--샘플데이터 of member
insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'test1@kh.com', '1234', '010-1111-1111','테스터1','남자','골프,독서','A0201', 'M0101');
insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'test2@kh.com', '1234', '010-1111-1112','테스터2','여자','골프,수영','A0202', 'M0102');
insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'admin1@kh.com', '1234','010-1111-1113','관리자1', '남자','등산,독서','A0203','M01A1');
insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'admin2@kh.com', '1234','010-1111-1114','관리자2', '여자','골프,독서','A0204','M01A2');
select * from member;
commit;

---------
--상품관리
--------
create table product(
    product_id  number(10),
    pname       varchar(30),
    quantity    number(10),
    price       number(10)
);
--기본키
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
create sequence product_product_id_seq;

--생성--
insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '컴퓨터', 5, 1000000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '모니터', 5, 500000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '프린터', 1, 300000);

--조회--
select product_id, pname, quantity, price
  from product
 where product_id = 2;

--수정--
update product
   set pname = '컴퓨터2',
       quantity = 10,
       price = 1200000;

--삭제
delete from product where product_id = 1;

--전체조회-
select product_id,pname,quantity,price from product;

commit;

---------
--첨부파일
---------
create table uploadfile(
    uploadfile_id   number(10),     --파일아이디
    code            varchar2(11),   --분류코드
    rid             varchar2(10),     --참조번호(게시글번호등)
    store_filename  varchar2(100),   --서버보관파일명
    upload_filename varchar2(100),   --업로드파일명(유저가 업로드한파일명)
    fsize           varchar2(45),   --업로드파일크기(단위byte)
    ftype           varchar2(100),   --파일유형(mimetype)
    cdate           timestamp default systimestamp, --등록일시
    udate           timestamp default systimestamp  --수정일시
);
--기본키
alter table uploadfile add constraint uploadfile_uploadfile_id_pk primary key(uploadfile_id);

--외래키
alter table uploadfile add constraint uploadfile_uploadfile_id_fk
    foreign key(code) references code(code_id);

--제약조건
alter table uploadfile modify code constraint uploadfile_code_nn not null;
alter table uploadfile modify rid constraint uploadfile_rid_nn not null;
alter table uploadfile modify store_filename constraint uploadfile_store_filename_nn not null;
alter table uploadfile modify upload_filename constraint uploadfile_upload_filename_nn not null;
alter table uploadfile modify fsize constraint uploadfile_fsize_nn not null;
alter table uploadfile modify ftype constraint uploadfile_ftype_nn not null;

--시퀀스
create sequence uploadfile_uploadfile_id_seq;

