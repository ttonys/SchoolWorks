#include "StdAfx.h"
#include "boss_ball.h"
#include "resource.h"


CImageList CBullets::m_Images;

CBullets::CBullets(int x, int y, int nMontion, int kk) :CGameObject(x, y), m_nMotion(nMontion), k(kk)
{

}

CBullets::~CBullets(void)
{
}
BOOL CBullets::Draw(CDC* pDC, BOOL bPause)
{
	if (!bPause)
	{
		if(k == 1)
		m_ptPos.y = m_ptPos.y + m_nMotion * 8;
		if (k == 3)
		{
			m_ptPos.y = m_ptPos.y + 8;
			m_ptPos.x = m_ptPos.x + 3;
		}
		if (k == 2)
		{
			m_ptPos.y = m_ptPos.y + 8;
			m_ptPos.x = m_ptPos.x - 3;
		}
	}

	if (m_ptPos.y > GAME_HEIGHT + BALL_HEIGHT)
		return FALSE;
	if (m_ptPos.y < -BALL_HEIGHT)
		return FALSE;

	m_Images.Draw(pDC, 0, m_ptPos, ILD_TRANSPARENT);

	return TRUE;
}

BOOL CBullets::LoadImage()
{
	return CGameObject::LoadImage(m_Images, IDB_BITMAP9, RGB(0, 0, 0), 8, 8, 1);
}