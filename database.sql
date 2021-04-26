  IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'QuanLyHocSinh')
  BEGIN
    CREATE DATABASE [QuanLyHocSinh]
  END
    GO
       USE [QuanLyHocSinh]
    GO
--You need to check if the table exists
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='HocSinh' and xtype='U')
BEGIN
    CREATE TABLE HocSinh (
        MHS INT PRIMARY KEY IDENTITY (1, 1),
        TenHS VARCHAR(100),
		Diem FLOAT,
		HinhAnh VARCHAR(100),
		DiaChi VARCHAR(100),
		GhiChu VARCHAR(100),
    )
END

SET IDENTITY_INSERT HocSinh ON