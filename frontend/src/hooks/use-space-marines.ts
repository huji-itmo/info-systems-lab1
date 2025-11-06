// hooks/useSpaceMarines.ts
import { paths } from "@/types/api.types";
import { useQuery } from "@tanstack/react-query";

type SpaceMarinesResponse =
    paths["/space-marines"]["get"]["responses"]["200"]["content"]["application/json"];

const fetchSpaceMarines = async (page: number, size: number) => {
    const res = await fetch(
        `/api/space-marines?${new URLSearchParams({
            page: page.toString(),
            size: size.toString(),
        })}`
    );
    if (!res.ok) {
        throw new Error("Failed to fetch space marines");
    }
    return res.json() as Promise<SpaceMarinesResponse>;
};

// Custom hook
export const useSpaceMarines = (page: number = 0, size: number = 20) => {
    return useQuery({
        queryKey: ["space-marines", page, size],
        queryFn: () => fetchSpaceMarines(page, size),
        staleTime: 1000 * 60 * 5, // 5 minutes
        // keepPreviousData: true,
    });
};
