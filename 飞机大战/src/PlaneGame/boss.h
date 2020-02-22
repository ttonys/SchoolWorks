#pragma once
#include "gameobject.h"

class CEnemy2 :public CGameObject
{
public:
	CEnemy2(void);
	~CEnemy2(void);

	BOOL Draw(CDC* pDC, BOOL bPause);

	static BOOL LoadImage();

	CRect GetRect()
	{
		return CRect(m_ptPos, CPoint(m_ptPos.x + ENEMY2_WIDTH, m_ptPos.y + ENEMY2_HEIGHT));
	}

	int GetMontion() const
	{
		return m_nMotion;
	}
	//是否可以开火发射子弹
	BOOL Fired();
private:
	static const int ENEMY2_WIDTH = 150;
	static const int ENEMY2_HEIGHT = 110;
	static CImageList m_Images;
	int    m_nMotion;//方向 1->向下 0->停止 -1->向上
					 //图像索引
	int m_nImgIndex;
	//速度
	int m_V;
	int    m_nWait;//发射延时
public:
	int blood;
};
