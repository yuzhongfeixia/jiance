@echo off

 REM ###########################################################
 REM # Windows Server 2003��Oracle���ݿ��Զ�����������ű�
 REM ###########################################################

 REM ȡ��ǰϵͳʱ��,���������ϵͳ��ͬ��ȡֵ��һ��
 set CURDATE=%date:~0,4%%date:~5,2%%date:~8,2%
 set CURTIME=%time:~0,2%

 REM Сʱ�����С��10������ǰ�油0
 if "%CURTIME%"==" 0" set CURTIME=00
 if "%CURTIME%"==" 1" set CURTIME=01
 if "%CURTIME%"==" 2" set CURTIME=02
 if "%CURTIME%"==" 3" set CURTIME=03
 if "%CURTIME%"==" 4" set CURTIME=04
 if "%CURTIME%"==" 5" set CURTIME=05
 if "%CURTIME%"==" 6" set CURTIME=06
 if "%CURTIME%"==" 7" set CURTIME=07
 if "%CURTIME%"==" 8" set CURTIME=08
 if "%CURTIME%"==" 9" set CURTIME=09

 set CURTIME=%CURTIME%%time:~3,2%%time:~6,2%

 REM ���������ߡ��û���������
 set OWNER=jiance

 set USER=jiance

 set PASSWORD=jiance
 set PREFIX=jiangsu
 
 set BACK_DIR=F:\js_db_bak
 set RAR_CMD=C:\Program Files (x86)\WinRAR\WinRAR.exe

 REM ����������Ŀ¼��Ŀ¼�ṹΪbackup/YYYYMMDD/
 if not exist "%BACK_DIR%\%CURDATE%"     mkdir %BACK_DIR%\%CURDATE%

 set CURDIR=%BACK_DIR%\%CURDATE%
 set FILENAME=%CURDIR%\%PREFIX%_%CURDATE%_%CURTIME%.DMP
 set EXPLOG=%CURDIR%\%PREFIX%_%CURDATE%_%CURTIME%_log.log

 REM ����ORACLE��exp������û�����
 exp %USER%/%PASSWORD%@jiangsu file=%FILENAME% log=%EXPLOG% owner=%OWNER% grants=n
 
 REM ѹ����ɾ��ԭ���ļ�
 "%RAR_CMD%" a -df "%CURDIR%.rar" "%CURDIR%"
 
 REM ɾ��30��ǰ��ѹ���ļ�
 forfiles /p "%BACK_DIR%" /m *.rar /d -30 /c "cmd /c del /f @file"

 exit 