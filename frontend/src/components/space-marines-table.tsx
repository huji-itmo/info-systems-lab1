// components/SpaceMarinesTable.tsx
"use client";

import * as React from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useSpaceMarines } from "@/hooks/use-space-marines";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination"; // 👈 your pagination
import { Skeleton } from "@/components/ui/skeleton";
import { Button } from "@/components/ui/button";

interface SpaceMarinesTableProps {
  pageSize?: number;
}

// Helper to generate page range with ellipsis
function generatePaginationRange(
  currentPage: number,
  totalPages: number,
  siblingCount = 1
): (number | "...")[] {
  const totalPageNumbers = siblingCount + 5; // 1 (first) + 2*sibling + 1 (last) + 2 (dots)

  if (totalPages <= totalPageNumbers) {
    return Array.from({ length: totalPages }, (_, i) => i + 1);
  }

  const leftSiblingIndex = Math.max(currentPage - siblingCount, 1);
  const rightSiblingIndex = Math.min(currentPage + siblingCount, totalPages);

  const shouldShowLeftEllipsis = leftSiblingIndex > 2;
  const shouldShowRightEllipsis = rightSiblingIndex < totalPages - 1;

  if (!shouldShowLeftEllipsis && shouldShowRightEllipsis) {
    const leftItemCount = 3 + 2 * siblingCount;
    return [
      ...Array.from({ length: leftItemCount }, (_, i) => i + 1),
      "...",
      totalPages,
    ];
  }

  if (shouldShowLeftEllipsis && !shouldShowRightEllipsis) {
    const rightItemCount = 3 + 2 * siblingCount;
    return [
      1,
      "...",
      ...Array.from(
        { length: rightItemCount },
        (_, i) => totalPages - rightItemCount + 1 + i
      ),
    ];
  }

  return [
    1,
    "...",
    ...Array.from(
      { length: rightSiblingIndex - leftSiblingIndex + 1 },
      (_, i) => leftSiblingIndex + i
    ),
    "...",
    totalPages,
  ];
}

export function SpaceMarinesTable({ pageSize = 10 }: SpaceMarinesTableProps) {
  const [page, setPage] = React.useState(0); // zero-based
  const { data, isLoading, isError, error } = useSpaceMarines(page, pageSize);

  const totalPages = data?.totalPages ?? 0;

  const paginationRange = React.useMemo(() => {
    return generatePaginationRange(page + 1, totalPages);
  }, [page, totalPages]);

  if (isError) {
    return (
      <div className="p-4 text-destructive">
        Error loading Space Marines: {(error as Error).message}
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {/* Table */}
      <div className="border rounded-md">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Chapter</TableHead>
              <TableHead>Weapon</TableHead>
              <TableHead>Health</TableHead>
              <TableHead>Loyal</TableHead>
              <TableHead>Category</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {isLoading
              ? Array.from({ length: pageSize }).map((_, i) => (
                <TableRow key={i}>
                  <TableCell><Skeleton className="w-6 h-4" /></TableCell>
                  <TableCell><Skeleton className="w-20 h-4" /></TableCell>
                  <TableCell><Skeleton className="w-10 h-4" /></TableCell>
                  <TableCell><Skeleton className="w-24 h-4" /></TableCell>
                  <TableCell><Skeleton className="w-8 h-4" /></TableCell>
                  <TableCell><Skeleton className="w-6 h-4" /></TableCell>
                  <TableCell><Skeleton className="w-16 h-4" /></TableCell>
                </TableRow>
              ))
              : data?.content.length ? (
                data.content.map((marine) => (
                  <TableRow key={marine.id}>
                    <TableCell className="font-mono">{marine.id}</TableCell>
                    <TableCell>{marine.name}</TableCell>
                    <TableCell className="font-mono">{marine.chapterId}</TableCell>
                    <TableCell>{marine.weaponType}</TableCell>
                    <TableCell>{marine.health}</TableCell>
                    <TableCell>
                      {marine.loyal === null ? "—" : marine.loyal ? "✅" : "❌"}
                    </TableCell>
                    <TableCell>{marine.category || "—"}</TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={7} className="h-24 text-center">
                    No Space Marines found.
                  </TableCell>
                </TableRow>
              )}
          </TableBody>
        </Table>
      </div>

      {/* Pagination */}
      {totalPages > 1 && (
        <Pagination>
          <PaginationContent>
            <PaginationItem>
              <PaginationPrevious
                href="#"
                onClick={(e) => {
                  e.preventDefault();
                  setPage((p) => Math.max(0, p - 1));
                }}
                isActive={!(page === 0 || isLoading)}
              />
            </PaginationItem>

            {paginationRange.map((pageItem, index) => {
              if (pageItem === "...") {
                return (
                  <PaginationItem key={`ellipsis-${index}`}>
                    <PaginationEllipsis />
                  </PaginationItem>
                );
              }

              const pageNum = pageItem as number;
              const isActive = pageNum === page + 1;

              return (
                <PaginationItem key={pageNum}>
                  <PaginationLink
                    href="#"
                    isActive={isActive}
                    onClick={(e) => {
                      e.preventDefault();
                      setPage(pageNum - 1); // convert to zero-based
                    }}
                  >
                    {pageNum}
                  </PaginationLink>
                </PaginationItem>
              );
            })}

            <PaginationItem>
              <PaginationNext
                href="#"
                onClick={(e) => {
                  e.preventDefault();
                  setPage((p) => (p < totalPages - 1 ? p + 1 : p));
                }}
                isActive={!(page >= totalPages - 1 || isLoading)}
              />
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      )}

      {/* Optional: Total count footer */}
      <div className="text-muted-foreground text-sm text-center">
        {data ? (
          <>
            Showing{" "}
            <strong>
              {page * pageSize + 1}–
              {Math.min((page + 1) * pageSize, data.totalElements)}
            </strong>{" "}
            of <strong>{data.totalElements}</strong> Space Marines
          </>
        ) : isLoading ? (
          "Loading..."
        ) : null}
      </div>
    </div>
  );
}
