#pragma once
#include "gameobject.h"

class CBomb :
	public CGameObject
{
public:
	CBomb(int x,int y);
	CBomb(int x, int y, int kk);
	~CBomb(void);

	BOOL Draw(CDC* pDC,BOOL bPause);

	static BOOL LoadImage();

	CRect GetRect()
	{
		return CRect(m_ptPos,CPoint(m_ptPos.x+10,m_ptPos.y+BOMB_HEIGHT));
	}
	CRect Scan()
	{
		return CRect(m_ptPos.x - 200, m_ptPos.y - 200, m_ptPos.x + 200, m_ptPos.y + 200);
	}
	void ch_path(CPoint my_bomb, int s);
	int k = 0;
	int f = 0;
	int x1, y1;
	int is_shoot = 0;
private:
	static const int BOMB_HEIGHT = 20;
	static CImageList m_Images;
	CPoint MY_Bomb;
};
