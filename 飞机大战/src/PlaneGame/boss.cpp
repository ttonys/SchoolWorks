#include "StdAfx.h"
#include "boss.h"
#include "resource.h"

CImageList CEnemy2::m_Images;

CEnemy2::CEnemy2(void)
: blood(500)
{
	//随机确定X位置
	m_ptPos.x = rand() % (GAME_WIDTH - ENEMY2_HEIGHT) + 1;

	//随机确定图像索引
	m_nImgIndex = rand() % 4;

	//根据图像索引确定方向
	m_nMotion = 1;
	m_ptPos.y = -ENEMY2_HEIGHT;
	if (m_nImgIndex % 2 != 0)//如果是图像索引是偶数
	{
		m_nMotion = 1;
		m_ptPos.y = GAME_HEIGHT + ENEMY2_HEIGHT;
	}
	//随机确定速度
	m_V = rand() % 6 + 2;

	m_nWait = 0;
	m_ptPos.x = 240 - ENEMY2_WIDTH;
	m_ptPos.y = 0;
}

CEnemy2::~CEnemy2(void)
{
}
BOOL CEnemy2::LoadImage()
{
	return CGameObject::LoadImage(m_Images, IDB_BITMAP7, RGB(0, 0, 0), 150, 120, 4);
}
BOOL CEnemy2::Draw(CDC* pDC, BOOL bPause)
{
	m_nWait++;
	if (m_nWait>20)
		m_nWait = 0;

	if (!bPause&&m_nMotion == 1)
	{
		m_ptPos.x = m_ptPos.x + m_nMotion * 3;
	}
	else if (!bPause&&m_nMotion == -1)
	{
		m_ptPos.x = m_ptPos.x + m_nMotion * 3;
	}

	if (m_ptPos.x <= 0)m_nMotion = 1;
	if (m_ptPos.x >= GAME_WIDTH - ENEMY2_WIDTH)m_nMotion = -1;

	m_Images.Draw(pDC, 0, m_ptPos, ILD_TRANSPARENT);
	//绘制血条
	CBrush b1(RGB(255, 2, 3));
	int rx = blood;
	if (blood <= 0) rx = 0;
	pDC->SelectObject(b1);
	pDC->Rectangle(m_ptPos.x - 5, m_ptPos.y + 10, m_ptPos.x + blood / 4 + 7, m_ptPos.y - 100);
	return TRUE;
}
BOOL CEnemy2::Fired()
{
	if (m_nWait == 1)
		return TRUE;
	else
		return FALSE;
}
