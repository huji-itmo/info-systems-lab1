import { MainContent } from "@/components/main-content";
import { Card, CardContent } from "@/components/ui/card";

export default function Home() {

  return (
    <div className="flex justify-center items-center w-full h-full">
      <Card className="w-[90%] h-[90%]">
        <CardContent className="flex justify-center items-center">
          <MainContent></MainContent>

        </CardContent>
      </Card>
    </div>
  );
}
