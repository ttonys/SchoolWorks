#pragma once
#include "gameobject.h"

class CEnemy1 :public CGameObject
{
public:
	CEnemy1(void);
	~CEnemy1(void);

	BOOL Draw(CDC* pDC, BOOL bPause);

	static BOOL LoadImage();
	CRect GetRect()
	{
		return CRect(m_ptPos, CPoint(m_ptPos.x + ENEMY1_WIDTH, m_ptPos.y + ENEMY1_HEIGHT));
	}

	int GetMontion() const
	{
		return m_nMotion;
	}
	//�Ƿ���Կ������ӵ�
	BOOL Fired();
private:
	static const int ENEMY1_WIDTH = 50;
	static const int ENEMY1_HEIGHT = 50;
	static CImageList m_Images;
	int    m_nMotion;//���� 1->���� 0->ֹͣ -1->����
	 //ͼ������
	int m_nImgIndex;
	//�ٶ�
	int m_V;
	int    m_nWait;//������ʱ
};
