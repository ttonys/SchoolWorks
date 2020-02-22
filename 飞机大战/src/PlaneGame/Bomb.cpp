#include "StdAfx.h"
#include "Bomb.h"
#include "resource.h"


CImageList CBomb::m_Images;


CBomb::CBomb(int x,int y):CGameObject(x,y)
{
	x1 = x;
	y1 = y;
}

CBomb::CBomb(int x, int y, int kk) : CGameObject(x, y), k(kk)
{
	x1 = x;
	y1 = y;
}

CBomb::~CBomb(void)
{
}
int GetKey(int nVirtKey)
{
	return (GetKeyState(nVirtKey) & 0x8000) ? 1 : 0;
}

BOOL CBomb::LoadImage()
{
	return CGameObject::LoadImage(m_Images,IDB_BITMAP15,RGB(0,0,0),20,14,1);
}
BOOL CBomb::Draw(CDC* pDC, BOOL bPause)
{
	if (!bPause)
	{
		if (GetKey(VK_TAB) == 1)//°´ÏÂÁËTAB¼ü
		{
			if (f == 1 && is_shoot == 1)
			{
				if (MY_Bomb.x > m_ptPos.x)
					m_ptPos.x = m_ptPos.x + 10;
				else if (MY_Bomb.x < m_ptPos.x)
					m_ptPos.x = m_ptPos.x - 10;
				if (MY_Bomb.y > m_ptPos.y)
					m_ptPos.y = m_ptPos.y + 15;
				else if (MY_Bomb.y < m_ptPos.y)
					m_ptPos.y = m_ptPos.y - 10;
			}
			if (k == 1)
				m_ptPos.y = m_ptPos.y - 8;
			else if (k == 3)
				{
					m_ptPos.y = m_ptPos.y - 8;
					m_ptPos.x = m_ptPos.x - 3;
				}
			else if (k == 2)
				{
					m_ptPos.y = m_ptPos.y - 8;
					m_ptPos.x = m_ptPos.x + 3;
				}

			else
				m_ptPos.y = m_ptPos.y - 8;
		}
		else
		{
			m_ptPos.y = m_ptPos.y - 8;
		}
	}
	if(m_ptPos.y < -BOMB_HEIGHT)
		return FALSE;

	m_Images.Draw(pDC,0,m_ptPos,ILD_TRANSPARENT);

	return TRUE;
}
void CBomb::ch_path(CPoint my_bomb, int s)
{
	is_shoot = 1;
	MY_Bomb = my_bomb;
	f = 1;
}