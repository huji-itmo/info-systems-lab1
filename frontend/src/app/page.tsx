"use client"

import { MainContent } from "@/components/main-content";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { useRouter } from "next/navigation";

export default function Home() {

  const router = useRouter();

  return (
    <div className="flex justify-center items-center w-full h-full">
      <Card className="w-[90%] h-[90%]">
        <CardContent className="flex flex-col justify-center items-center gap-2">
          <Button variant={"outline"} onClick={() => router.push("/special-operations")}>
            Go to special operations
          </Button>

          <MainContent></MainContent>

        </CardContent>
      </Card>
    </div>
  );
}
