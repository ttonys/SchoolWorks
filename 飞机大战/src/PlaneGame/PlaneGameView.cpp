// PlaneGameView.cpp : CPlaneGameView 类的实现
//

#include "stdafx.h"
#include "PlaneGame.h"
#include <windows.h>
#include <mmsystem.h> 
#pragma  comment(lib,   "winmm.lib") 
#include "PlaneGameDoc.h"
#include "PlaneGameView.h"
#include "MyPlane.h"
#include "Enemy.h"
#include "Bomb.h"
#include "Ball.h"
#include "Explosion.h"
#include <atlimage.h>
#include "boss.h"
#include "boss_ball.h"
#include "prop.h"
#include "prop2.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif
static int count;

// CPlaneGameView

IMPLEMENT_DYNCREATE(CPlaneGameView, CView)

BEGIN_MESSAGE_MAP(CPlaneGameView, CView)
	// 标准打印命令
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
	ON_WM_TIMER()

END_MESSAGE_MAP()

// CPlaneGameView 构造/析构

CPlaneGameView::CPlaneGameView():m_pMe(NULL)
{
	// TODO: 在此处添加构造代码
}

CPlaneGameView::~CPlaneGameView()
{
}

BOOL CPlaneGameView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: 在此处通过修改
	//  CREATESTRUCT cs 来修改窗口类或样式

	return CView::PreCreateWindow(cs);
}

// CPlaneGameView 绘制

void CPlaneGameView::OnDraw(CDC* /*pDC*/)
{
	CPlaneGameDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: 在此处为本机数据添加绘制代码
}


// CPlaneGameView 打印

BOOL CPlaneGameView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// 默认准备
	return DoPreparePrinting(pInfo);
}

void CPlaneGameView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: 添加额外的打印前进行的初始化过程
}

void CPlaneGameView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: 添加打印后进行的清理过程
}


// CPlaneGameView 诊断

#ifdef _DEBUG
void CPlaneGameView::AssertValid() const
{
	CView::AssertValid();
}

void CPlaneGameView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CPlaneGameDoc* CPlaneGameView::GetDocument() const // 非调试版本是内联的
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CPlaneGameDoc)));
	return (CPlaneGameDoc*)m_pDocument;
}
#endif //_DEBUG


// CPlaneGameView 消息处理程序
void CPlaneGameView::OnInitialUpdate()
{
	CView::OnInitialUpdate();
	// TODO: 在此添加专用代码和/或调用基类
	//初始化游戏
	InitGame();
	PlaySound(MAKEINTRESOURCE(IDR_WAVE3), AfxGetResourceHandle(), SND_ASYNC | SND_RESOURCE | SND_LOOP | SND_NODEFAULT);
}
void CPlaneGameView::StopGame()
{
	delete m_pMe;
	delete m_pMemDC;
	delete m_pDC;
	delete m_pMemBitmap;
}

BOOL CPlaneGameView::InitGame()
{
	CRect rc;
	GetClientRect(rc);
	isStarted = FALSE;
	//产生随机数种子
	srand( (unsigned)time( NULL ) );

	//建立设备DC
	m_pDC = new CClientDC(this);

	//建立内存DC
	m_pMemDC = new CDC;
	m_pMemDC->CreateCompatibleDC(m_pDC);

	//建立内存位图
	m_pMemBitmap = new CBitmap;
	m_pMemBitmap->CreateCompatibleBitmap(m_pDC,GAME_WIDTH,GAME_HEIGHT);

	//将位图选入内存DC
	m_pMemDC->SelectObject(m_pMemBitmap);

	CMyPlane::LoadImage();
	CEnemy::LoadImage();
	CBomb::LoadImage();
	CBall::LoadImage();
	CExplosion::LoadImage();
	CEnemy2::LoadImage();
	CBullets::LoadImage();
	CEnemy1::LoadImage();
	CEnemy3::LoadImage();
	//产生主角(战机)
	m_pMe = new CMyPlane;

	//启动游戏
	SetTimer(1,30,NULL);

	return TRUE;
}

void CPlaneGameView::UpdateFrame(CDC* pMemDC)
{
	//绘制天空
//	pMemDC->FillSolidRect(0,0,GAME_WIDTH,GAME_HEIGHT,RGB(84, 142, 239));
	if (GetKey('P') == 1)
	{
		KillTimer(1);
		if (AfxMessageBox(_T("是否继续？点是继续，点否退出。"), MB_YESNO) == 6)
			SetTimer(1, 20, NULL);
		else
			exit(1);

	}
	//动态背景图
	CRect m_client;
	GetClientRect(&m_client);
	CDC memDC;
	memDC.CreateCompatibleDC(pMemDC);
	CBitmap bmpDraw;
	bmpDraw.LoadBitmapW(IDB_BITMAP3);
	CBitmap* pbmpOld = memDC.SelectObject(&bmpDraw);
	pMemDC->BitBlt(0, 0, m_client.Width(), m_client.Height(), &memDC, 0, 0, SRCCOPY);
	pMemDC->BitBlt(0, 0, m_client.Width(), count, &memDC, 0, m_client.Height() - count, SRCCOPY);
	pMemDC->BitBlt(0, count, m_client.Width(), m_client.Height() - count, &memDC, 0, 0, SRCCOPY);
	if (level <= 3)
	{
		count += 3;                       //控制背景移动速度
		if (count > m_client.Height())
			count = 0;
	}
	//胜利背景
	if (level > 3)
	{
		CBitmap bitmap;
		bitmap.LoadBitmapW(IDB_BITMAP19);
		CDC dcCompatible;
		dcCompatible.CreateCompatibleDC(pMemDC);
		dcCompatible.SelectObject(&bitmap);

		CRect rect;
		GetClientRect(&rect);
		pMemDC->BitBlt(150, 300, rect.Width(), rect.Height(), &dcCompatible, 0, 0, SRCCOPY);
		count = 0;
	}
		

	//显示积分，魔法值，提示信息
	CString str1 = _T("Score:");
	sprintf(buffer, "%d\0", score);
	CString str2 = _T("魔法值:");
	sprintf(buffer2, "%d\0", magic);
	CString str3 = _T("关卡:");
	sprintf(buffer3, "%d\0",level );
	CString str4 = _T("F键全屏技能,消耗50魔法值!");
	CString str5 = _T("R键开启,E键关闭无敌模式");
	CString str6 = _T("P键暂停");
	CString str7 = _T("TAB切换为导弹追踪模式");
	pMemDC->SetBkMode(TRANSPARENT);
	pMemDC->SetTextAlign(TA_CENTER);
	pMemDC->SetTextColor(RGB(0, 255, 0));
	pMemDC->TextOut(GAME_WIDTH - 150, GAME_HEIGHT - 940, str1);
	pMemDC->TextOut(GAME_WIDTH - 50, GAME_HEIGHT - 940, CString(buffer));
	pMemDC->TextOut(GAME_WIDTH - 150, GAME_HEIGHT - 915, str2);
	pMemDC->TextOut(GAME_WIDTH - 50, GAME_HEIGHT - 915, CString(buffer2));
	pMemDC->TextOut(GAME_WIDTH - 150, GAME_HEIGHT - 890, str3);
	pMemDC->TextOut(GAME_WIDTH - 50, GAME_HEIGHT - 890, CString(buffer3));
	pMemDC->SetTextColor(RGB(0, 0, 255));
	pMemDC->TextOut(GAME_WIDTH - 680, GAME_HEIGHT - 900, str4);
	pMemDC->TextOut(GAME_WIDTH - 685, GAME_HEIGHT - 870, str5);
	pMemDC->TextOut(GAME_WIDTH - 758, GAME_HEIGHT - 840, str6);
	pMemDC->TextOut(GAME_WIDTH - 688, GAME_HEIGHT - 810, str7);
	//生命显示
	if (life == 3)
	{
		CBitmap bitmap;
		bitmap.LoadBitmapW(IDB_BITMAP6);
		CDC dcCompatible;
		dcCompatible.CreateCompatibleDC(pMemDC);
		dcCompatible.SelectObject(&bitmap);
		CRect rect;
		GetClientRect(&rect);
		pMemDC->BitBlt(0, 0, 50, 50, &dcCompatible, 0, 0, SRCCOPY);
		pMemDC->BitBlt(50, 0, 50, 50, &dcCompatible, 0, 0, SRCCOPY);
		pMemDC->BitBlt(100, 0, 50, 50, &dcCompatible, 0, 0, SRCCOPY);
	}
	if (life == 2)
	{
		CBitmap bitmap;
		bitmap.LoadBitmapW(IDB_BITMAP6);
		CDC dcCompatible;
		dcCompatible.CreateCompatibleDC(pMemDC);
		dcCompatible.SelectObject(&bitmap);
		CRect rect;
		GetClientRect(&rect);
		pMemDC->BitBlt(0, 0, 50, 50, &dcCompatible, 0, 0, SRCCOPY);
		pMemDC->BitBlt(50, 0, 50, 50, &dcCompatible, 0, 0, SRCCOPY);
	}
	if (life == 1)
	{
		CBitmap bitmap;
		bitmap.LoadBitmapW(IDB_BITMAP6);
		CDC dcCompatible;
		dcCompatible.CreateCompatibleDC(pMemDC);
		dcCompatible.SelectObject(&bitmap);
		CRect rect;
		GetClientRect(&rect);
		pMemDC->BitBlt(0, 0, 50, 50, &dcCompatible, 0, 0, SRCCOPY);
	}
	//绘制我方战机
	if(m_pMe!=NULL)
	{
	   m_pMe->Draw(m_pMemDC,FALSE);
	}
	else
	{   //Game Over
		if (level <= 3)
		{
			CString str = _T("挑战失败!");
			pMemDC->SetBkMode(TRANSPARENT);
			pMemDC->SetTextAlign(TA_CENTER);
			pMemDC->SetTextColor(RGB(255, 0, 0));
			pMemDC->TextOut(GAME_WIDTH / 2, GAME_HEIGHT / 2, str);
		}

	}
	
	//绘制 导弹、爆炸、敌机、子弹 道具.....
	for(int i=0;i<9;i++)
	{
		POSITION pos1,pos2;
		for( pos1 = m_ObjList[i].GetHeadPosition(); ( pos2 = pos1 ) != NULL; )
		{
			CGameObject* pObj = (CGameObject*)m_ObjList[i].GetNext( pos1 );
			if(!pObj->Draw(pMemDC,FALSE))
			{
				m_ObjList[i].RemoveAt(pos2);
				delete pObj;
			}
		}
	}

	//复制内存DC到设备DC
	m_pDC->BitBlt(0,0,GAME_WIDTH,GAME_HEIGHT,m_pMemDC,0,0,SRCCOPY);
}
void CPlaneGameView::AI()
{
	//随机产生boss
//	if (life != 0 && (score == 1000 || score == 1700 || score == 2700) && score != 0 && is_creat == 0)
	if (life != 0 && (score == 400 || score == 1000 || score == 1700) && score != 0 && is_creat == 0)
	{
		score += 10;
		m_ObjList[enEnemy2].AddTail(new CEnemy2);
		is_creat = 1;
	}
	static int nCreator = rand() %5+10;
	//随机产生敌机
	if(nCreator<=0 && level != 4)
	{
		nCreator = rand()%5+10;
		m_ObjList[enEnemy].AddTail(new CEnemy);
	}
	nCreator--;
	//产生道具
	if (score % 200 == 0)
	{
		kind = rand() % 2;
		score = score + 10;
		if(kind == 0)
			m_ObjList[enEnemy1].AddTail(new CEnemy1);
		if(kind == 1)
			m_ObjList[enEnemy3].AddTail(new CEnemy3);
	}
	if(m_pMe==NULL)
		return;
	//检测四个方向键，移动战机
	for(int i=0;i<4;i++)
	{
		int nMeMotion=0;
		m_pMe->SetVerMotion(0);
		m_pMe->SetHorMotion(0);

		nMeMotion = GetKey(VK_UP);
		if(nMeMotion==1)
			m_pMe->SetVerMotion(1);
		    
		nMeMotion = GetKey(VK_DOWN);
		if(nMeMotion==1)
			m_pMe->SetVerMotion(-1);

		nMeMotion = GetKey(VK_RIGHT);
		if(nMeMotion==1)
			m_pMe->SetHorMotion(1);

		nMeMotion = GetKey(VK_LEFT);
		if(nMeMotion==1)
			m_pMe->SetHorMotion(-1);
	}
	//产生战机导弹
	if(GetKey(VK_SPACE)==1 && GetKey(VK_TAB) == 0)//按下了空格键
	{
			if (m_pMe != NULL && m_pMe->Fired())
			{
				CPoint pt = m_pMe->GetPoint();
				m_ObjList[enBomb].AddTail(new CBomb(pt.x+ 10, pt.y + 10));
				m_ObjList[enBomb].AddTail(new CBomb(pt.x + 30, pt.y + 10));
				m_ObjList[enBomb].AddTail(new CBomb(pt.x + 50, pt.y + 10));
			}
	}
	else if(GetKey(VK_SPACE) == 1 && GetKey(VK_TAB) == 1)
	{
		if (m_pMe != NULL && m_pMe->Fired())
		{
			CPoint pt = m_pMe->GetPoint();
			m_ObjList[enBomb].AddTail(new CBomb(pt.x + 10, pt.y + 20, 2));
			m_ObjList[enBomb].AddTail(new CBomb(pt.x + 30, pt.y + 20, 1));
			m_ObjList[enBomb].AddTail(new CBomb(pt.x + 50, pt.y + 20, 3));
		}
	}
	//判断无敌状态
	if (GetKey('R'))
	{
		invincible = 1;
	}
	else if (GetKey('E'))
	{
		invincible = 0;
	}
	//敌机发射子弹
	CPoint PlanePt = m_pMe->GetPoint();
	for(POSITION ePos=m_ObjList[enEnemy].GetHeadPosition();ePos!=NULL;)
	{
          CEnemy* pEnemy = (CEnemy*)m_ObjList[enEnemy].GetNext(ePos);
		  if(!pEnemy->Fired())
			  continue;
		  CPoint  ePt = pEnemy->GetPoint();

		  BOOL by=FALSE;

		  //敌机在战机前面
		  if(pEnemy->GetMontion()==1 && ePt.y<PlanePt.y)
		         by=  TRUE;
		  //敌机在战机后面
		  if(pEnemy->GetMontion()==-1 && ePt.y>PlanePt.y)
				 by=  TRUE;	

		  if (level == 1)
		  {
			  if (rand() % 4 == 0)
			  {
				  m_ObjList[enBall].AddTail(new CBall(ePt.x + 30, ePt.y + 10, pEnemy->GetMontion()));
			  }
		  }
		  if (level == 2)
		  {
			  if (rand() % 4 == 0|| rand() % 4 == 1)
			  {
				  m_ObjList[enBall].AddTail(new CBall(ePt.x + 30, ePt.y + 10, pEnemy->GetMontion()));
			  }
		  }
		  if (level == 3)
		  {
			  if(rand() % 4 == 0 || rand() % 4 == 1 || rand() % 4 == 3)
				  m_ObjList[enBall].AddTail(new CBall(ePt.x + 30, ePt.y + 10, pEnemy->GetMontion()));
		  }
	}
	//boss发射子弹
	for (POSITION ePos = m_ObjList[enEnemy2].GetHeadPosition(); ePos != NULL;)
	{
		CEnemy2* pEnemy2 = (CEnemy2*)m_ObjList[enEnemy2].GetNext(ePos);
		if (!pEnemy2->Fired())
			continue;
		CPoint  boPt = pEnemy2->GetPoint();

		if (level == 1)
		{
			m_ObjList[enBall1].AddTail(new CBullets(boPt.x + 25, boPt.y + 10, 1, 1));
		}
		if (level == 2)
		{
			m_ObjList[enBall1].AddTail(new CBullets(boPt.x + 10, boPt.y + 10, 1, 1));
			m_ObjList[enBall1].AddTail(new CBullets(boPt.x + 25, boPt.y + 10, 1, 1));
			m_ObjList[enBall1].AddTail(new CBullets(boPt.x + 40, boPt.y + 10, 1, 1));
		}
		if (level == 3)
		{
			m_ObjList[enBall1].AddTail(new CBullets(boPt.x + 10, boPt.y + 10, 1, 2));
			m_ObjList[enBall1].AddTail(new CBullets(boPt.x + 25, boPt.y + 10, 1, 1));
			m_ObjList[enBall1].AddTail(new CBullets(boPt.x + 40, boPt.y + 10, 1, 3));
		}
	}

	//敌机子弹炸掉战机
	POSITION bPos1 = NULL, bPos2 = NULL;
	CRect mRect = m_pMe->GetRect();
	for (bPos1 = m_ObjList[enBall].GetHeadPosition(); (bPos2 = bPos1) != NULL;)
	{
		CBall* pBall = (CBall*)m_ObjList[enBall].GetNext(bPos1);
		CRect bRect = pBall->GetRect();
		CRect tmpRect;
		if (tmpRect.IntersectRect(&bRect, mRect))
		{
			//添加爆炸效果
			m_ObjList[enExplosion].AddTail(
				new CExplosion(mRect.left, mRect.top)
			);

			//删除子弹
			m_ObjList[enBall].RemoveAt(bPos2);
			delete pBall;

			//删除战机
			if(invincible == 0)
			   life--;
			if (life == 0)
			{
				delete m_pMe;
				m_pMe = NULL;
				break;
			}		
		}
	}
	//boss子弹炸掉战机
	for (bPos1 = m_ObjList[enBall1].GetHeadPosition(); (bPos2 = bPos1) != NULL;)
	{
		CBall* pBall = (CBall*)m_ObjList[enBall1].GetNext(bPos1);
		CRect bRect = pBall->GetRect();
		CRect tmpRect;
		if (tmpRect.IntersectRect(&bRect, mRect))
		{
			//添加爆炸效果
			m_ObjList[enExplosion].AddTail(
				new CExplosion(mRect.left, mRect.top)
			);

			//删除子弹
			m_ObjList[enBall1].RemoveAt(bPos2);
			delete pBall;

			//删除战机
			if (invincible == 0)
				life--;
			if (life == 0)
			{
				delete m_pMe;
				m_pMe = NULL;
				break;
			}
		}
	}
	//判断清屏是否可用
	int f = 0;
	if (magic >= 50 && GetKey('F'))
		f = 1, magic = magic - 50;
	 //敌机和战机碰撞
	POSITION ePos1 = NULL, ePos2 = NULL;
	for (ePos1 = m_ObjList[enEnemy].GetHeadPosition(); (ePos2 = ePos1) != NULL;)
	{
		CEnemy* pEnemy = (CEnemy*)m_ObjList[enEnemy].GetNext(ePos1);
		CRect pRect = pEnemy->GetRect();
		CRect tmpRect;
		//按下了F键
		if (f == 1)
		{
			if (GetKey('F'))
			{
				if (m_pMe != NULL)
				{
					m_ObjList[enExplosion].AddTail(new CExplosion(pRect.left, pRect.top));
					m_ObjList[enEnemy].RemoveAt(ePos2);
					delete pEnemy;
					continue;
				}
			}
		}
		if (tmpRect.IntersectRect(&pRect, mRect))
		{
			//添加爆炸效果
			m_ObjList[enExplosion].AddTail(
				new CExplosion(mRect.left, mRect.top)
			);
			//删除战机
			if(invincible == 0)
			    life--;
			if (life == 0)
			{
				delete m_pMe;
				m_pMe = NULL;
				break;
			}
			//m_pMe = new CMyPlane;
			//删除敌机
			m_ObjList[enEnemy].RemoveAt(ePos2);
			delete pEnemy;
			score = score + 10;
			break;

		}
	}
	//战机导弹炸掉敌机
	POSITION mPos1=NULL,mPos2=NULL;
	for(mPos1=m_ObjList[enBomb].GetHeadPosition();(mPos2=mPos1)!=NULL;)
	{
		CBomb* pBomb = (CBomb*)m_ObjList[enBomb].GetNext(mPos1);
		CRect bRect = pBomb->GetRect();
		CRect scan_bRect = pBomb->Scan();
		POSITION ePos1=NULL,ePos2=NULL;
		for(ePos1=m_ObjList[enEnemy].GetHeadPosition();(ePos2=ePos1)!=NULL;)
		{
			CEnemy* pEnemy = (CEnemy*)m_ObjList[enEnemy].GetNext(ePos1);
			CRect mRect = pEnemy->GetRect();
			CRect tmpRect;
			pBomb->f = 0;
			if(tmpRect.IntersectRect(&bRect,mRect))
			{
				//计分
				score = score + 10;
				//增加魔法值
				magic = magic + 2;
				if (magic >= 100)
					magic = 100;
				//添加爆炸效果
				m_ObjList[enExplosion].AddTail(
					new CExplosion(mRect.left,mRect.top)
					);
				//删除导弹
				m_ObjList[enBomb].RemoveAt(mPos2);
				delete pBomb;

				//删除敌机
				m_ObjList[enEnemy].RemoveAt(ePos2);
				delete pEnemy;
				break;
			}
			//导弹追踪
			else if (tmpRect.IntersectRect(&scan_bRect, mRect))
			{
					pEnemy->LOCK();
					int is_ok = pEnemy->get_lock();
					pBomb->ch_path(mRect.CenterPoint(), is_ok);
					
			}
		}
	}
	//战机导弹炸掉boss
	for (mPos1 = m_ObjList[enBomb].GetHeadPosition(); (mPos2 = mPos1) != NULL;)
	{
		CBomb* pBomb = (CBomb*)m_ObjList[enBomb].GetNext(mPos1);
		CRect bRect = pBomb->GetRect();

		POSITION ePos1 = NULL, ePos2 = NULL;
		for (ePos1 = m_ObjList[enEnemy2].GetHeadPosition(); (ePos2 = ePos1) != NULL;)
		{
			CEnemy2* pEnemy2 = (CEnemy2*)m_ObjList[enEnemy2].GetNext(ePos1);
			CRect mRect = pEnemy2->GetRect();
			CRect tmpRect;
			if (tmpRect.IntersectRect(&bRect, mRect))
			{
				if(level == 1)
					pEnemy2->blood -= 10;
				if(level == 2)
					pEnemy2->blood -= 5;
				if(level == 3)
					pEnemy2->blood -= 3;
				//添加爆炸效果
				m_ObjList[enExplosion].AddTail(
					new CExplosion(mRect.left, mRect.top)
				);
				//删除导弹
				m_ObjList[enBomb].RemoveAt(mPos2);
				delete pBomb;

				//删除敌机
				if (pEnemy2->blood <= 0)
				{
					m_ObjList[enEnemy2].RemoveAt(ePos2);
					delete pEnemy2;
					level++;
					is_creat = 0;
					break;
				}
			}
		}
	}
	//道具下落
	if (kind == 0)
	{
		for (ePos1 = m_ObjList[enEnemy1].GetHeadPosition(); (ePos2 = ePos1) != NULL;)
		{
			CEnemy1* pEnemy1 = (CEnemy1*)m_ObjList[enEnemy1].GetNext(ePos1);
			CRect eRect = pEnemy1->GetRect();
			CRect tmpRect;
			if (tmpRect.IntersectRect(eRect, mRect))
			{
				//删除物品
				m_ObjList[enEnemy1].RemoveAt(ePos2);
				delete pEnemy1;
				if (life >= 3)
					life = 3;
				else
					life++;
			}
		}
	}
	else if (kind == 1)
		{
			for (ePos1 = m_ObjList[enEnemy3].GetHeadPosition(); (ePos2 = ePos1) != NULL;)
			{
				CEnemy3* pEnemy1 = (CEnemy3*)m_ObjList[enEnemy3].GetNext(ePos1);
				CRect eRect = pEnemy1->GetRect();
				CRect tmpRect;
				if (tmpRect.IntersectRect(eRect, mRect))
				{
					//删除物品
					m_ObjList[enEnemy3].RemoveAt(ePos2);
					delete pEnemy1;
					magic = magic + 10;
					if (magic >= 100)
						magic = 100;
				}
			}
	}
	//游戏结束
	if (level == 4)
	{
		delete m_pMe;
		m_pMe = NULL;
	}

}

void CPlaneGameView::OnTimer(UINT_PTR nIDEvent)
{
	//刷新游戏帧画面: 在内存DC上绘图
	UpdateFrame(m_pMemDC);
	AI();
	//
	CView::OnTimer(nIDEvent);
}


