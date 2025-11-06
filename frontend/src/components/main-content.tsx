"use client"

import { useState } from "react";
import { SpaceMarinesTable } from "./space-marines-table";
import { AddSpaceMarineDialogContent } from "./add-space-marine-dialog";
import { Dialog } from "@radix-ui/react-dialog";
import { DialogTrigger } from "./ui/dialog";
import { Button } from "./ui/button";

export function MainContent() {
    const [open, setOpen] = useState(false);

    return <div className="flex flex-col justify-center gap-2">
        <Dialog>
            <DialogTrigger asChild>
                <Button>asd</Button>
            </DialogTrigger>
            <AddSpaceMarineDialogContent setOpen={setOpen}></AddSpaceMarineDialogContent>

        </Dialog>

        <SpaceMarinesTable>
        </SpaceMarinesTable>
    </div>

}
