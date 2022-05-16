
> [MSDN] #WinDBG Command
```
https://docs.microsoft.com/ko-kr/windows-hardware/drivers/debugger/commands
```


> Symbol 정보
```
[심볼 정보]
SRV*c:\symbols*http://msdl.microsoft.com/download/symbols
```

> lm (모듈 리스트)
```
https://docs.microsoft.com/ko-kr/windows-hardware/drivers/debugger/lm--list-loaded-modules-
6: kd> lm
start             end                 module name
00000000`00400000 00000000`0041e000   NOTEPAD   (deferred)             
00000000`10000000 00000000`1017a000   7z         (deferred)                        
00000000`570a0000 00000000`570a9000   X3DAudio   (deferred)             
000001a2`14ca0000 000001a2`14ca3000   XAudio   (deferred)             
000001a2`6b870000 000001a2`6ccfa000   icudtco   (deferred)        
```


> [User Level Context Switching]
```
https://docs.microsoft.com/ko-kr/windows-hardware/drivers/debugger/-process
!process 0 0 asf.exe
PROCESS ffffb305512e3580
    SessionId: 1  Cid: 2b40    Peb: 45f78ab000  ParentCid: 05cc
    DirBase: 373500002  ObjectTable: ffffc98b9d456040  HandleCount: 1996.
    Image: asf.exe

https://docs.microsoft.com/ko-kr/windows-hardware/drivers/debugger/-process--set-process-context-
6: kd> .process ffffb305512e3580
Implicit process is now ffffb305`512e3580
```

> [Disassemble]
```
u nodepad::Function L100
00007ff7`89f821f0 48894c2408      mov     qword ptr [rsp+8],rcx
00007ff7`89f821f5 57              push    rdi
00007ff7`89f821f6 4883ec40        sub     rsp,40h
00007ff7`89f821fa 48c7442420feffffff mov   qword ptr [rsp+20h],0FFFFFFFFFFFFFFFEh
00007ff7`89f82203 48895c2458      mov     qword ptr [rsp+58h],rbx
00007ff7`89f82208 4889742468      mov     qword ptr [rsp+68h],rsi
00007ff7`89f8220d 488bf9          mov     rdi,rcx
```

> [Display Type] # Class, Struct, Memory 등의 정보를 출력
```
0:004> dt ntdll!_PEB
   +0x000 InheritedAddressSpace : UChar
   +0x001 ReadImageFileExecOptions : UChar
   +0x002 BeingDebugged    : UChar
   +0x003 BitField         : UChar
   +0x003 ImageUsesLargePages : Pos 0, 1 Bit
   +0x003 IsProtectedProcess : Pos 1, 1 Bit
   +0x003 IsImageDynamicallyRelocated : Pos 2, 1 Bit
   +0x003 SkipPatchingUser32Forwarders : Pos 3, 1 Bit
   +0x003 IsPackagedProcess : Pos 4, 1 Bit
   +0x003 IsAppContainer   : Pos 5, 1 Bit
   +0x003 IsProtectedProcessLight : Pos 6, 1 Bit
   +0x003 IsLongPathAwareProcess : Pos 7, 1 Bit
   +0x004 Padding0         : [4] UChar

0:004> dqs notepad
00007ff7`102c0000  00000003`00905a4d
00007ff7`102c0008  0000ffff`00000004
00007ff7`102c0010  00000000`000000b8
00007ff7`102c0018  00000000`00000040
00007ff7`102c0020  00000000`00000000
00007ff7`102c0028  00000000`00000000
00007ff7`102c0030  00000000`00000000
00007ff7`102c0038  000000f8`00000000
00007ff7`102c0040  cd09b400`0eba1f0e
00007ff7`102c0048  685421cd`4c01b821
00007ff7`102c0050  72676f72`70207369
00007ff7`102c0058  6f6e6e61`63206d61
00007ff7`102c0060  6e757220`65622074
```

> module info
```
0:004> lmvmnotepad
Browse full module list
start             end                 module name
00007ff7`102c0000 00007ff7`102f8000   notepad    (pdb symbols)          c:\symbols\notepad.pdb\3A57B9BF130E0EFECD8099EAED40BD6E1\notepad.pdb
    Loaded symbol image file: C:\Windows\system32\notepad.exe
    Image path: C:\Windows\system32\notepad.exe
    Image name: notepad.exe
    Browse all global symbols  functions  data
    Image was built with /Brepro flag.
    Timestamp:        4178AED3 (This is a reproducible build file hash, not a timestamp)
    CheckSum:         00037B81
    ImageSize:        00038000
    File version:     10.0.19041.1566
    Product version:  10.0.19041.1566
    File flags:       0 (Mask 3F)
    File OS:          40004 NT Win32
    File type:        1.0 App
    File date:        00000000.00000000
    Translations:     0409.04b0
    Information from resource tables:
        CompanyName:      Microsoft Corporation
        ProductName:      Microsoft® Windows® Operating System
        InternalName:     Notepad
        OriginalFilename: NOTEPAD.EXE
        ProductVersion:   10.0.19041.1566
        FileVersion:      10.0.19041.1566 (WinBuild.160101.0800)
        FileDescription:  Notepad
        LegalCopyright:   © Microsoft Corporation. All rights reserved.
```


> [Search Memory]
```
https://docs.microsoft.com/ko-kr/windows-hardware/drivers/debugger/s--search-memory-
6: kd> s 7ff7`87290000 l?1`00000000 43 21 22 33 45
00007ff7`8be7d830  43 21 22 33 45 7f 00 00-30 80 4c 87 f7 7f 00 00  .:......0.L.....
```


> [Check Image] 이미지 변경 검사
```
6: kd> !chkimg -d Notepad
Error for notepad: Could not find image file for the module. Make sure binaries are included in the symbol path.
(이미지가 에러 나는 경우 symobl 폴더에 있는 notepad의 원본파일을 lmDvmNotepad 명령어의 CheckSum, ImageSize로 맞춰줘야함)
(최신 windbg에서 !chkimg 가 기본으로 빠짐 그럴때 .load ext 모듈 로드 필요)
```




> 파일 Lanch 오픈 시 심볼로드가 안될 경우
```
.reload /f 
```

> 논페이지 서치
```
!poolfind None
!poolused
```

> 커널 영역에서 구동시
```
!process 0 0 System
```

> Raw Memory에서 PE 이미지 정보 보기
```
!dh 0x10000000
```

> 메모리 Basic information (vad) 알아내기
```
0:004> !vprot notepad
BaseAddress:       00007ff7102c0000
AllocationBase:    00007ff7102c0000
AllocationProtect: 00000080  PAGE_EXECUTE_WRITECOPY
RegionSize:        0000000000001000
State:             00001000  MEM_COMMIT
Protect:           00000002  PAGE_READONLY
Type:              01000000  MEM_IMAGE
```

> 프로세스의 전체 스레드
```
!process ffffe08821c9e300 <- !process 0 0 notepad.exe 했을때 EProcess
```


> 32Bit 프로세스를 검색할때
```
!process 0 0 후 'Peb: 0' 패턴을 찾으면 됨.
PROCESS ffff8b8b498af0c0
    SessionId: 0  Cid: 0b94    Peb: 00713000  ParentCid: 02e4
    DirBase: 14ec21000  ObjectTable: ffffd107db3fa9c0  HandleCount: 431.
    Image: spoolsv.exe
```

> dps 포인터로 보는 명령어 poi
```
0:004> dps poi(00007ff7`102c0000)
00000003`00905a4d  ????????`????????
00000003`00905a55  ????????`????????
00000003`00905a5d  ????????`????????
00000003`00905a65  ????????`????????
00000003`00905a6d  ????????`????????
00000003`00905a75  ????????`????????
00000003`00905a7d  ????????`????????
00000003`00905a85  ????????`????????
00000003`00905a8d  ????????`????????
00000003`00905a95  ????????`????????
00000003`00905a9d  ????????`????????
00000003`00905aa5  ????????`????????
00000003`00905aad  ????????`????????
```

> PTE에 Execute 권한이 있는지 보는법- E가 있을 시 Execute 영역
```
7: kd> !pte FFFFC706`FC10DB13
                                           VA ffffc706fc10db13
PXE at FFFFF5FAFD7EBC70    PPE at FFFFF5FAFD78E0D8    PDE at FFFFF5FAF1C1BF00    PTE at FFFFF5E3837E0868
contains 0A00000003CC0863  contains 0A00000354901863  contains 0A000003C46CF863  contains 0A000001D264A963
pfn 3cc0      ---DA--KWEV  pfn 354901    ---DA--KWEV  pfn 3c46cf    ---DA--KWEV  pfn 1d264a    -G-DA--KWEV

```


> 함수 풀어서 함수 끝날때 까지 보는 명령어
```
0:004> uf CreateFileW
KERNEL32!CreateFileW:
00007ffa`afd94b60 ff25eac50500    jmp     qword ptr [KERNEL32!_imp_CreateFileW (00007ffa`afdf1150)]  Branch

KERNEL32!_imp_FindClose+0x2:
00007ffa`afdf1122 e5ae            in      eax,0AEh
00007ffa`afdf1124 fa              cli
00007ffa`afdf1125 7f00            jg      KERNEL32!_imp_FindClose+0x7 (00007ffa`afdf1127)  Branch

KERNEL32!_imp_FindClose+0x7:
00007ffa`afdf1127 0030            add     byte ptr [rax],dh
00007ffa`afdf1129 3ee5ae          in      eax,0AEh
00007ffa`afdf112c fa              cli
00007ffa`afdf112d 7f00            jg      KERNEL32!_imp_FileTimeToLocalFileTime+0x7 (00007ffa`afdf112f)  Branch

KERNEL32!_imp_FileTimeToLocalFileTime+0x7:
00007ffa`afdf112f 0090cdf4aefa    add     byte ptr [rax-5510B33h],dl
00007ffa`afdf1135 7f00            jg      KERNEL32!_imp_DeleteVolumeMountPointW+0x7 (00007ffa`afdf1137)  Branch

```

> 메모리 내용 파일에 쓰기
```
.writemem C:\\Users\\uu\\Desktop\\LGel.mem fffff68a`1d051000 L?27000
```


> SwishDbgExt 사용법
```
!load swishdbgext
함수 프롤로그 Hook 탐지
!ms_process /pid 0x18DC /all /scan
```

> 모듈 전체 Chkimg 하기
```
!for_each_module !chkimg @#ModuleName
- 모듈을 읽을수 없는 error가 나면 심볼로드가 안된거임 이렇게 해줌됨-> !for_each_module u @#ModuleName
```


> 속한 Page Pool 정보 알아내기
```
0: kd> !pool ffffb70f`32163470
```


> 전체 콜스택 보기(call stack)
```
~*kb
```


> integrity_level 알아내는법
```
!process [EPROCESS] 후 Ctrl+break 해보면 Token에 해당하는 주소
dt nt!_TOKEN ffffa38bbb65c0a0
```

> 각 프로세스를 돌면서 특정 주소를 dps 하는 명령어
```
!for_each_process ".echo searching=> @#Process; .process /r @#Process; dqs ADDR"
```

> 심볼 검색하는법
```
0: kd> x /D /a Kernel32!CreateFile*
 A B C D E F G H I J K L M N O P Q R S T U V W X Y Z

00007ffa`afd8bcb0 KERNEL32!CreateFileMappingA (CreateFileA)
00007ffa`afd8c8e0 KERNEL32!CreateFileMappingWStub (CreateFileA)
00007ffa`afd94b40 KERNEL32!CreateFile2 (CreateFileA)
00007ffa`afd94b50 KERNEL32!CreateFileA (CreateFileA)
00007ffa`afd94b60 KERNEL32!CreateFileW (CreateFileA)
00007ffa`afda9970 KERNEL32!CreateFileMappingNumaWStub (CreateFileA)
00007ffa`afdd1250 KERNEL32!CreateFileTransactedA (CreateFileA)
00007ffa`afdd1310 KERNEL32!CreateFileTransactedW (CreateFileA)
00007ffa`afdd1470 KERNEL32!CreateFileTransactedW$fin$0 (CreateFileA)
00007ffa`afdd1dc0 KERNEL32!CreateFileMappingNumaA (CreateFileA)
```


> 검색 중 특정 데이터만 보기
```
.foreach (addr { s-[1]q 0 L?1`00000000 0000000000000fdc }) { dps addr L1 }
패턴 찾기
.foreach (addr { s-[1]b ffffe70f`05dc2000 L?1`00000000 33 10 00 00 }) { dps addr - 0x4 L1 }
```


> WinDbg 에서 nonpaged pool 영역에서 EPROCESS 뒤지는 명령 (Win10 기준)
```
.foreach (place {s -[1]b (스캔 시작 주소) l? (스캔할 크기) 01 00 14 00 00 00 00 00 ff 0f 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00}) {!process place - 50 0}
(ex)
.foreach (place {s -[1]b ffffc702`00000000 l? 1`00000000 01 00 14 00 00 00 00 00 ff 0f 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00}) {!process place - 50 0}
```



> Nonpaged Pool에서 VAD 찾는 방법
```
.process /p /r ffffc88416a66080
00007FF4`B8680000 기준 s ffffc884`00000000 l? 2`00000000 80 86 4b ff

ffffc884`16e8fcf8  80 86 4b ff 9d 87 4b ff-07 07 00 00 00 00 00 00  ..K...K.........
0: kd> dt _MMVAD_SHORT ffffc884`16e8fce0
nt!_MMVAD_SHORT
   +0x000 NextVad          : 0xffffc884`17636ec0 _MMVAD_SHORT
   +0x008 ExtraCreateInfo  : 0xffffc884`157b1390 Void
   +0x000 VadNode          : _RTL_BALANCED_NODE
   +0x018 StartingVpn      : 0xff4b8680
   +0x01c EndingVpn        : 0xff4b879d
   +0x020 StartingVpnHigh  : 0x7 ''
   +0x021 EndingVpnHigh    : 0x7 ''
   +0x022 CommitChargeHigh : 0 ''
   +0x023 SpareNT64VadUChar : 0 ''
   +0x024 ReferenceCount   : 0n0
   +0x028 PushLock         : _EX_PUSH_LOCK
   +0x030 u                : <unnamed-tag>
   +0x034 u1               : <unnamed-tag>

   StartingVpnHigh + startingVpn + 000;
   7ff4b8680000 으로 계산 됨
```
