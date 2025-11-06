import { paths } from '@/types/api.types';

type SpaceMarineCreateRequest =
  paths['/space-marines']['post']['requestBody']['content']['application/json'];

export const createSpaceMarine = async (data: SpaceMarineCreateRequest) => {
  const res = await fetch(`/api/space-marines`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    throw new Error('Failed to create Space Marine');
  }

  return res.json();
};
