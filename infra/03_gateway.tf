resource "aws_internet_gateway" "miracle_igw" {
  vpc_id = aws_vpc.miracle_vpc.id

  tags = {
    Name = "miracle-igw"
  }
}

// ---

resource "aws_eip" "miracle_nat_eip_a" {}

resource "aws_nat_gateway" "miracle_nat_a" {
  allocation_id = aws_eip.miracle_nat_eip_a.id
  subnet_id     = aws_subnet.miracle_public_a.id

  tags = {
    Name = "miracle-nat-a"
  }
}

resource "aws_eip" "miracle_nat_eip_c" {}

resource "aws_nat_gateway" "miracle_nat_c" {
  allocation_id = aws_eip.miracle_nat_eip_c.id
  subnet_id     = aws_subnet.miracle_public_c.id

  tags = {
    Name = "miracle-nat-c"
  }
}
