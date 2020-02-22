#include "StdAfx.h"
#include "prop2.h"
#include "resource.h"
CImageList CEnemy3::m_Images;
CEnemy3::CEnemy3(void)
{
	//���ȷ��Xλ��
	m_ptPos.x = rand() % (GAME_WIDTH - ENEMY1_HEIGHT) + 1;

	//���ȷ��ͼ������
	m_nImgIndex = rand() % 1;

	//����ͼ������ȷ������
	m_nMotion = 1;
	m_ptPos.y = -ENEMY1_HEIGHT;
	if (m_nImgIndex % 2 != 0)//�����ͼ��������ż��
	{
		m_nMotion = 1;
		m_ptPos.y = GAME_HEIGHT + ENEMY1_HEIGHT;
	}
	//���ȷ���ٶ�
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