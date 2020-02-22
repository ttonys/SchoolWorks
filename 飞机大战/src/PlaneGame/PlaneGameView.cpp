// PlaneGameView.cpp : CPlaneGameView ���ʵ��
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
	// ��׼��ӡ����
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
	ON_WM_TIMER()

END_MESSAGE_MAP()

// CPlaneGameView ����/����

CPlaneGameView::CPlaneGameView():m_pMe(NULL)
{
	// TODO: �ڴ˴���ӹ������
}

CPlaneGameView::~CPlaneGameView()
{
}

BOOL CPlaneGameView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: �ڴ˴�ͨ���޸�
	//  CREATESTRUCT cs ���޸Ĵ��������ʽ

	return CView::PreCreateWindow(cs);
}

// CPlaneGameView ����

void CPlaneGameView::OnDraw(CDC* /*pDC*/)
{
	CPlaneGameDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: �ڴ˴�Ϊ����������ӻ��ƴ���
}


// CPlaneGameView ��ӡ

BOOL CPlaneGameView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// Ĭ��׼��
	return DoPreparePrinting(pInfo);
}

void CPlaneGameView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: ��Ӷ���Ĵ�ӡǰ���еĳ�ʼ������
}

void CPlaneGameView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: ��Ӵ�ӡ����е��������
}


// CPlaneGameView ���

#ifdef _DEBUG
void CPlaneGameView::AssertValid() const
{
	CView::AssertValid();
}

void CPlaneGameView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CPlaneGameDoc* CPlaneGameView::GetDocument() const // �ǵ��԰汾��������
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CPlaneGameDoc)));
	return (CPlaneGameDoc*)m_pDocument;
}
#endif //_DEBUG


// CPlaneGameView ��Ϣ�������
void CPlaneGameView::OnInitialUpdate()
{
	CView::OnInitialUpdate();
	// TODO: �ڴ����ר�ô����/����û���
	//��ʼ����Ϸ
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
	//�������������
	srand( (unsigned)time( NULL ) );

	//�����豸DC
	m_pDC = new CClientDC(this);

	//�����ڴ�DC
	m_pMemDC = new CDC;
	m_pMemDC->CreateCompatibleDC(m_pDC);

	//�����ڴ�λͼ
	m_pMemBitmap = new CBitmap;
	m_pMemBitmap->CreateCompatibleBitmap(m_pDC,GAME_WIDTH,GAME_HEIGHT);

	//��λͼѡ���ڴ�DC
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
	//��������(ս��)
	m_pMe = new CMyPlane;

	//������Ϸ
	SetTimer(1,30,NULL);

	return TRUE;
}

void CPlaneGameView::UpdateFrame(CDC* pMemDC)
{
	//�������
//	pMemDC->FillSolidRect(0,0,GAME_WIDTH,GAME_HEIGHT,RGB(84, 142, 239));
	if (GetKey('P') == 1)
	{
		KillTimer(1);
		if (AfxMessageBox(_T("�Ƿ���������Ǽ���������˳���"), MB_YESNO) == 6)
			SetTimer(1, 20, NULL);
		else
			exit(1);

	}
	//��̬����ͼ
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
		count += 3;                       //���Ʊ����ƶ��ٶ�
		if (count > m_client.Height())
			count = 0;
	}
	//ʤ������
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
		

	//��ʾ���֣�ħ��ֵ����ʾ��Ϣ
	CString str1 = _T("Score:");
	sprintf(buffer, "%d\0", score);
	CString str2 = _T("ħ��ֵ:");
	sprintf(buffer2, "%d\0", magic);
	CString str3 = _T("�ؿ�:");
	sprintf(buffer3, "%d\0",level );
	CString str4 = _T("F��ȫ������,����50ħ��ֵ!");
	CString str5 = _T("R������,E���ر��޵�ģʽ");
	CString str6 = _T("P����ͣ");
	CString str7 = _T("TAB�л�Ϊ����׷��ģʽ");
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
	//������ʾ
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
	//�����ҷ�ս��
	if(m_pMe!=NULL)
	{
	   m_pMe->Draw(m_pMemDC,FALSE);
	}
	else
	{   //Game Over
		if (level <= 3)
		{
			CString str = _T("��սʧ��!");
			pMemDC->SetBkMode(TRANSPARENT);
			pMemDC->SetTextAlign(TA_CENTER);
			pMemDC->SetTextColor(RGB(255, 0, 0));
			pMemDC->TextOut(GAME_WIDTH / 2, GAME_HEIGHT / 2, str);
		}

	}
	
	//���� ��������ը���л����ӵ� ����.....
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

	//�����ڴ�DC���豸DC
	m_pDC->BitBlt(0,0,GAME_WIDTH,GAME_HEIGHT,m_pMemDC,0,0,SRCCOPY);
}
void CPlaneGameView::AI()
{
	//�������boss
//	if (life != 0 && (score == 1000 || score == 1700 || score == 2700) && score != 0 && is_creat == 0)
	if (life != 0 && (score == 400 || score == 1000 || score == 1700) && score != 0 && is_creat == 0)
	{
		score += 10;
		m_ObjList[enEnemy2].AddTail(new CEnemy2);
		is_creat = 1;
	}
	static int nCreator = rand() %5+10;
	//��������л�
	if(nCreator<=0 && level != 4)
	{
		nCreator = rand()%5+10;
		m_ObjList[enEnemy].AddTail(new CEnemy);
	}
	nCreator--;
	//��������
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
	//����ĸ���������ƶ�ս��
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
	//����ս������
	if(GetKey(VK_SPACE)==1 && GetKey(VK_TAB) == 0)//�����˿ո��
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
	//�ж��޵�״̬
	if (GetKey('R'))
	{
		invincible = 1;
	}
	else if (GetKey('E'))
	{
		invincible = 0;
	}
	//�л������ӵ�
	CPoint PlanePt = m_pMe->GetPoint();
	for(POSITION ePos=m_ObjList[enEnemy].GetHeadPosition();ePos!=NULL;)
	{
          CEnemy* pEnemy = (CEnemy*)m_ObjList[enEnemy].GetNext(ePos);
		  if(!pEnemy->Fired())
			  continue;
		  CPoint  ePt = pEnemy->GetPoint();

		  BOOL by=FALSE;

		  //�л���ս��ǰ��
		  if(pEnemy->GetMontion()==1 && ePt.y<PlanePt.y)
		         by=  TRUE;
		  //�л���ս������
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
	//boss�����ӵ�
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

	//�л��ӵ�ը��ս��
	POSITION bPos1 = NULL, bPos2 = NULL;
	CRect mRect = m_pMe->GetRect();
	for (bPos1 = m_ObjList[enBall].GetHeadPosition(); (bPos2 = bPos1) != NULL;)
	{
		CBall* pBall = (CBall*)m_ObjList[enBall].GetNext(bPos1);
		CRect bRect = pBall->GetRect();
		CRect tmpRect;
		if (tmpRect.IntersectRect(&bRect, mRect))
		{
			//��ӱ�ըЧ��
			m_ObjList[enExplosion].AddTail(
				new CExplosion(mRect.left, mRect.top)
			);

			//ɾ���ӵ�
			m_ObjList[enBall].RemoveAt(bPos2);
			delete pBall;

			//ɾ��ս��
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
	//boss�ӵ�ը��ս��
	for (bPos1 = m_ObjList[enBall1].GetHeadPosition(); (bPos2 = bPos1) != NULL;)
	{
		CBall* pBall = (CBall*)m_ObjList[enBall1].GetNext(bPos1);
		CRect bRect = pBall->GetRect();
		CRect tmpRect;
		if (tmpRect.IntersectRect(&bRect, mRect))
		{
			//��ӱ�ըЧ��
			m_ObjList[enExplosion].AddTail(
				new CExplosion(mRect.left, mRect.top)
			);

			//ɾ���ӵ�
			m_ObjList[enBall1].RemoveAt(bPos2);
			delete pBall;

			//ɾ��ս��
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
	//�ж������Ƿ����
	int f = 0;
	if (magic >= 50 && GetKey('F'))
		f = 1, magic = magic - 50;
	 //�л���ս����ײ
	POSITION ePos1 = NULL, ePos2 = NULL;
	for (ePos1 = m_ObjList[enEnemy].GetHeadPosition(); (ePos2 = ePos1) != NULL;)
	{
		CEnemy* pEnemy = (CEnemy*)m_ObjList[enEnemy].GetNext(ePos1);
		CRect pRect = pEnemy->GetRect();
		CRect tmpRect;
		//������F��
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
			//��ӱ�ըЧ��
			m_ObjList[enExplosion].AddTail(
				new CExplosion(mRect.left, mRect.top)
			);
			//ɾ��ս��
			if(invincible == 0)
			    life--;
			if (life == 0)
			{
				delete m_pMe;
				m_pMe = NULL;
				break;
			}
			//m_pMe = new CMyPlane;
			//ɾ���л�
			m_ObjList[enEnemy].RemoveAt(ePos2);
			delete pEnemy;
			score = score + 10;
			break;

		}
	}
	//ս������ը���л�
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
				//�Ʒ�
				score = score + 10;
				//����ħ��ֵ
				magic = magic + 2;
				if (magic >= 100)
					magic = 100;
				//��ӱ�ըЧ��
				m_ObjList[enExplosion].AddTail(
					new CExplosion(mRect.left,mRect.top)
					);
				//ɾ������
				m_ObjList[enBomb].RemoveAt(mPos2);
				delete pBomb;

				//ɾ���л�
				m_ObjList[enEnemy].RemoveAt(ePos2);
				delete pEnemy;
				break;
			}
			//����׷��
			else if (tmpRect.IntersectRect(&scan_bRect, mRect))
			{
					pEnemy->LOCK();
					int is_ok = pEnemy->get_lock();
					pBomb->ch_path(mRect.CenterPoint(), is_ok);
					
			}
		}
	}
	//ս������ը��boss
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
				//��ӱ�ըЧ��
				m_ObjList[enExplosion].AddTail(
					new CExplosion(mRect.left, mRect.top)
				);
				//ɾ������
				m_ObjList[enBomb].RemoveAt(mPos2);
				delete pBomb;

				//ɾ���л�
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
	//��������
	if (kind == 0)
	{
		for (ePos1 = m_ObjList[enEnemy1].GetHeadPosition(); (ePos2 = ePos1) != NULL;)
		{
			CEnemy1* pEnemy1 = (CEnemy1*)m_ObjList[enEnemy1].GetNext(ePos1);
			CRect eRect = pEnemy1->GetRect();
			CRect tmpRect;
			if (tmpRect.IntersectRect(eRect, mRect))
			{
				//ɾ����Ʒ
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
					//ɾ����Ʒ
					m_ObjList[enEnemy3].RemoveAt(ePos2);
					delete pEnemy1;
					magic = magic + 10;
					if (magic >= 100)
						magic = 100;
				}
			}
	}
	//��Ϸ����
	if (level == 4)
	{
		delete m_pMe;
		m_pMe = NULL;
	}

}

void CPlaneGameView::OnTimer(UINT_PTR nIDEvent)
{
	//ˢ����Ϸ֡����: ���ڴ�DC�ϻ�ͼ
	UpdateFrame(m_pMemDC);
	AI();
	//
	CView::OnTimer(nIDEvent);
}


