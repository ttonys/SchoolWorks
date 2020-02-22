#include "StdAfx.h"
#include "prop2.h"
#include "resource.h"
CImageList CEnemy3::m_Images;
CEnemy3::CEnemy3(void)
{
	//随机确定X位置
	m_ptPos.x = rand() % (GAME_WIDTH - ENEMY1_HEIGHT) + 1;

	//随机确定图像索引
	m_nImgIndex = rand() % 1;

	//根据图像索引确定方向
	m_nMotion = 1;
	m_ptPos.y = -ENEMY1_HEIGHT;
	if (m_nImgIndex % 2 != 0)//如果是图像索引是偶数
	{
		m_nMotion = 1;
		m_ptPos.y = GAME_HEIGHT + ENEMY1_HEIGHT;
	}
	//随机确定速度
	m_V = rand() % 6 + 4;
	m_nWait = 0;
}

CEnemy3::~CEnemy3(void)
{
}

BOOL CEnemy3::LoadImage()
{
//	return CGameObject::LoadImage(m_Images, IDB_BITMAP11, RGB(0, 0, 0), 50, 50, 1);
		return CGameObject::LoadImage(m_Images, IDB_BITMAP12, RGB(0, 0, 0), 50, 50, 1);
}
BOOL CEnemy3::Draw(CDC* pDC, BOOL bPause)
{
	m_nWait++;
	if (m_nWait>20)
		m_nWait = 0;

	if (!bPause)
	{
		m_ptPos.y = m_ptPos.y + m_nMotion * m_V;
	}

	if (m_ptPos.y > GAME_HEIGHT + ENEMY1_HEIGHT)
		return FALSE;
	if (m_ptPos.y < -ENEMY1_HEIGHT)
		return FALSE;

	m_Images.Draw(pDC, m_nImgIndex, m_ptPos, ILD_TRANSPARENT);

	return TRUE;
}
BOOL CEnemy3::Fired()
{
	if (m_nWait == 0)
		return TRUE;
	else
		return FALSE;
}