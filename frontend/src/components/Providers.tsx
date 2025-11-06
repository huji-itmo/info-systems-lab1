"use client";

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactNode } from "react";
import { Toaster } from "./ui/sonner";

// Create the QueryClient **inside** the Client Component
function makeQueryClient() {
    return new QueryClient({
        defaultOptions: {
            queries: {
                staleTime: 1000 * 60 * 5, // 5 minutes
                retry: 1,
            },
        },
    });
}

// Use React state to store the client, so it's stable across renders
export function Providers({ children }: { children: ReactNode }) {
    const queryClient = makeQueryClient();

    return (
        <QueryClientProvider client={queryClient}>
            {children}
            <Toaster />
        </QueryClientProvider>
    );
}
