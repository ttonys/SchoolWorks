// PlaneGameView.h : CPlaneGameView ��Ľӿ�
//


#pragma once
class CMyPlane;
class CPlaneGameView : public CView
{

	enum ObjType{enEnemy,enBomb,enBall,enExplosion,enProps, enEnemy2,enBall1,enEnemy1,enEnemy3
	};

protected: // �������л�����
	CPlaneGameView();
	DECLARE_DYNCREATE(CPlaneGameView)

// ����
public:
	CPlaneGameDoc* GetDocument() const;
	BOOL isStarted;
	CImageList startIMG;
	CRect rect;//������Ļ����
// ����
public:

// ��д
public:
	virtual void OnDraw(CDC* pDC);  // ��д�Ի��Ƹ���ͼ
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// ʵ��
public:
	virtual ~CPlaneGameView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	//�ڴ�DC
	CDC*        m_pMemDC;
	//�豸DC
	CClientDC*  m_pDC;
	//�ڴ�λͼ
	CBitmap*    m_pMemBitmap;


protected:
	//��ʼ����Ϸ
	BOOL InitGame();
	int life = 3;
	char buffer[10];
	char buffer2[10];
	char buffer3[10];
	int score = 0;
	int invincible = 0;
	int magic = 0;
	int level = 1;
	int is_creat = 0;
	int kind = 0;
	//������Ϸ
	void StopGame();

	//ˢ����Ϸ��֡����
	void UpdateFrame(CDC* pMemDC);


	void AI();

	//��ü���״̬1->down
	int GetKey(int nVirtKey)
	{
		return (GetKeyState(nVirtKey) & 0x8000)? 1 : 0;
	}
	
	CMyPlane*  m_pMe;

	CObList    m_ObjList[9];



// ���ɵ���Ϣӳ�亯��
protected:
	DECLARE_MESSAGE_MAP()
public:
	virtual void OnInitialUpdate();
	afx_msg void OnTimer(UINT_PTR nIDEvent);
};

#ifndef _DEBUG  // PlaneGameView.cpp �еĵ��԰汾
inline CPlaneGameDoc* CPlaneGameView::GetDocument() const
   { return reinterpret_cast<CPlaneGameDoc*>(m_pDocument); }
#endif

